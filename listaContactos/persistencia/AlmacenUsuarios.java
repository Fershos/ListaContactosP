package listaContactos.persistencia;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import listaContactos.contactos.Usuario;
import listaContactos.contactos.Contacto;
import listaContactos.contactos.SolicitudImporte;

public class AlmacenUsuarios {
  private static LinkedList<Usuario> usuarios = new LinkedList<>();

  public static LinkedList<Usuario> getUsuarios() {
    return usuarios;
  }

  public static void guardarUsuario(Usuario usuario) {
    usuarios.push(usuario);
  }

  public static String respaldarUsuarios() {
    LinkedList<Usuario> respaldo = new LinkedList<>();
    try {
      FileOutputStream file = new FileOutputStream("contactos.save");
      ObjectOutputStream oos = new ObjectOutputStream(file);
      for(Usuario user : usuarios) {
        byte[] pass = user.getPassword();
        Usuario usrRespaldo = cifrarUsuario(pass, user);

        for(Contacto contacto : user.getListaContactos())
          usrRespaldo.registrarContacto(cifrarContacto(pass, contacto));

        for(SolicitudImporte solicitud : user.getSolicitudes())
          usrRespaldo.agregarSolicitud(new SolicitudImporte(Cifrador.cifrar(pass, solicitud.getUsuarioSolicitante())));

        respaldo.push(usrRespaldo);
      }
      oos.writeObject(respaldo);
      oos.close();

    } catch(IOException e) {
      return "ERROR: No se pudo crear el archivo de respaldo";
    }

    return "Archivo creado exitosamente...";
  }

  private static Usuario cifrarUsuario(byte[] pass, Usuario user) {
    Usuario usrRespaldo = new Usuario(Cifrador.cifrar(pass, user.getUsuario()),
                                      Cifrador.cifrar(pass, user.getNombre()),
                                      pass,
                                      Cifrador.cifrar(pass, user.getEMail()));
    return usrRespaldo;
  }

  private static Usuario decifrarUsuario(byte[] pass, Usuario user) {
    Usuario usrDec = new Usuario(Cifrador.decifrar(pass, user.getUsuario()),
                                      Cifrador.decifrar(pass, user.getNombre()),
                                      pass,
                                      Cifrador.decifrar(pass, user.getEMail()));
    return usrDec;
  }

  private static Contacto cifrarContacto(byte[] pass, Contacto contacto) {
    Contacto cntRespaldo = new Contacto(Cifrador.cifrar(pass, contacto.getNombre()),
                                        Cifrador.cifrar(pass, contacto.getTelefono()),
                                        Cifrador.cifrar(pass, contacto.getEMail()),
                                        Cifrador.cifrar(pass, contacto.getUrl()));
    return cntRespaldo;
  }

  private static Contacto decifrarContacto(byte[] pass, Contacto contacto) {
    Contacto cntDec = new Contacto(Cifrador.decifrar(pass, contacto.getNombre()),
                                        Cifrador.decifrar(pass, contacto.getTelefono()),
                                        Cifrador.decifrar(pass, contacto.getEMail()),
                                        Cifrador.decifrar(pass, contacto.getUrl()));
    return cntDec;
  }

  public static String cargarUsuarios() {
    try {
      FileInputStream file = new FileInputStream("contactos.save");
      ObjectInputStream ois = new ObjectInputStream(file);
      LinkedList<Usuario> cifrados = (LinkedList<Usuario>) ois.readObject();
      for(Usuario usr : cifrados) {
        byte[] pass = usr.getPassword();
        Usuario usrDec = decifrarUsuario(pass, usr);

        for(Contacto contacto : usr.getListaContactos())
          usrDec.registrarContacto(decifrarContacto(pass, contacto));

        for(SolicitudImporte solicitud : usr.getSolicitudes())
          usrDec.agregarSolicitud(new SolicitudImporte(Cifrador.decifrar(pass, solicitud.getUsuarioSolicitante())));

        usuarios.push(usrDec);
      }
      ois.close();

    } catch(IOException e){
      return "ERROR: No se pudo abrir el archivo de respaldo";
    } catch(ClassNotFoundException c) {
      return "ERROR: Clase no encotrada";
    }

    return "Usuarios cargados exitosamente...";
  }
}
