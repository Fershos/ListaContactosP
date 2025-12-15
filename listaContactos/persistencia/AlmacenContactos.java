package listaContactos.persistencia;

import java.util.HashMap;
import java.util.LinkedList;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import listaContactos.contactos.Contacto;
import listaContactos.contactos.Usuario;

public class AlmacenContactos {
  private static HashMap<String, LinkedList<Contacto>> contactos = new HashMap<>();

  public static void agregarContacto(String usuario, Contacto contacto) {
    LinkedList<Contacto> cont = contactos.get(usuario);
    try {
      cont.add(contacto);
    } catch(NullPointerException e) {
      contactos.put(usuario, new LinkedList<>());
      cont = contactos.get(usuario);
      cont.add(contacto);
    }
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

  public static String cargarContactos() {
    try {
      FileInputStream file = new FileInputStream("contactos.save");
      ObjectInputStream ois = new ObjectInputStream(file);
      HashMap<String, LinkedList<Contacto>> cifrados = (HashMap<String, LinkedList<Contacto>>) ois.readObject();
      for(Usuario usuario : AlmacenUsuarios.getUsuarios()) {
        String usr = usuario.getUsuario();
        byte[] pass = usuario.getPassword();
        contactos.put(usr, decifrarListaContacto(cifrados.get(usr), pass));
      }
      ois.close();

    } catch(IOException e){
      return "ERROR: No se pudo abrir el archivo de respaldo";
    } catch(ClassNotFoundException c) {
      return "ERROR: Clase no encotrada";
    }

    return "Contactos cargados exitosamente...";
  }

  private static LinkedList<Contacto> decifrarListaContacto(LinkedList<Contacto> cifrados, byte[] password) {
    LinkedList<Contacto> des = new LinkedList<>();
    if(cifrados == null)
      return null;

    for(Contacto contacto : cifrados)
      des.add(decifrarContacto(password, contacto));

    return des;
  }

  public static String respaldarContactos() {
    HashMap<String, LinkedList<Contacto>> respaldo = new HashMap<>();
    try {
      FileOutputStream file = new FileOutputStream("contactos.save");
      ObjectOutputStream oos = new ObjectOutputStream(file);
      for(Usuario usuario : AlmacenUsuarios.getUsuarios()) {
        String usr = usuario.getUsuario();
        byte[] pass = usuario.getPassword();
        respaldo.put(usr, cifrarListaContaco(contactos.get(usr), pass));
      }
      oos.writeObject(respaldo);
      oos.close();

    } catch(IOException e) {
      return "ERROR: No se pudo crear el archivo de respaldo";
    }

    return "Archivo creado exitosamente...";
  }

  private static LinkedList<Contacto> cifrarListaContaco(LinkedList<Contacto> crudos, byte[] password) {
    LinkedList<Contacto> cif = new LinkedList<>();
    try {
      for(Contacto contacto : crudos)
        cif.add(cifrarContacto(password, contacto));
    } catch(NullPointerException e) {
      cif = null;
    }

    return cif;
  }

  public static LinkedList<Contacto> getContactos(String usuario) {
    return contactos.get(usuario);
  }
}
