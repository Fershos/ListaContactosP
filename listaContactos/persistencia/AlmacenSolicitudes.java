package listaContactos.persistencia;

import java.util.HashMap;
import java.util.LinkedList;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import listaContactos.contactos.SolicitudImporte;
import listaContactos.contactos.Usuario;

public class AlmacenSolicitudes {
  private static HashMap<String, LinkedList<SolicitudImporte>> solicitudes = new HashMap<>();

  public static void agregarSolicitud(String usuario, SolicitudImporte solicitud) {
    LinkedList<SolicitudImporte> sol = solicitudes.get(usuario);
    try {
      sol.add(solicitud);
    } catch(NullPointerException e) {
      solicitudes.put(usuario, new LinkedList<>());
      sol = solicitudes.get(usuario);
      sol.add(solicitud);
    }
  }

  private static SolicitudImporte cifrarSolicitud(byte[] pass, SolicitudImporte solicitud) {
    SolicitudImporte solRespaldo = new SolicitudImporte(Cifrador.cifrar(pass, solicitud.getUsuarioSolicitante()));
    return solRespaldo;
  }

  private static SolicitudImporte decifrarSolicitud(byte[] pass, SolicitudImporte solicitud) {
    SolicitudImporte solDec = new SolicitudImporte(Cifrador.decifrar(pass, solicitud.getUsuarioSolicitante()));
    return solDec;
  }

  public static String cargarSolicitudes() {
    try {
      FileInputStream file = new FileInputStream("solicitudes.save");
      ObjectInputStream ois = new ObjectInputStream(file);
      HashMap<String, LinkedList<SolicitudImporte>> cifrados = (HashMap<String, LinkedList<SolicitudImporte>>) ois.readObject();
      for(Usuario usuario : AlmacenUsuarios.getUsuarios()) {
        String usr = usuario.getUsuario();
        byte[] pass = usuario.getPassword();
        solicitudes.put(usr, decifrarListaSolicitudes(cifrados.get(usr), pass));
      }
      ois.close();

    } catch(IOException e){
      return "ERROR: No se pudo abrir el archivo de respaldo";
    } catch(ClassNotFoundException c) {
      return "ERROR: Clase no encotrada";
    }

    return "Solicitudes cargados exitosamente...";
  }

  private static LinkedList<SolicitudImporte> decifrarListaSolicitudes(LinkedList<SolicitudImporte> cifrados, byte[] password) {
    LinkedList<SolicitudImporte> des = new LinkedList<>();
    if(cifrados == null)
      return null;

    for(SolicitudImporte solicitud : cifrados)
      des.push(decifrarSolicitud(password, solicitud));

    return des;
  }

  public static String respaldarSolicitudes() {
    HashMap<String, LinkedList<SolicitudImporte>> respaldo = new HashMap<>();
    try {
      FileOutputStream file = new FileOutputStream("solicitudes.save");
      ObjectOutputStream oos = new ObjectOutputStream(file);
      for(Usuario usuario : AlmacenUsuarios.getUsuarios()) {
        String usr = usuario.getUsuario();
        byte[] pass = usuario.getPassword();
        respaldo.put(usr, cifrarListaSolicitudes(solicitudes.get(usr), pass));
      }
      oos.writeObject(respaldo);
      oos.close();

    } catch(IOException e) {
      return "ERROR: No se pudo crear el archivo de respaldo";
    }

    return "Archivo creado exitosamente...";
  }

  private static LinkedList<SolicitudImporte> cifrarListaSolicitudes(LinkedList<SolicitudImporte> crudos, byte[] password) {
    LinkedList<SolicitudImporte> cif = new LinkedList<>();
    try {
      for(SolicitudImporte solicitud : crudos)
        cif.push(cifrarSolicitud(password, solicitud));
    } catch(NullPointerException e) {
      cif = null;
    }
    return cif;
  }

  public static LinkedList<SolicitudImporte> getSolicitudes(String usuario) {
    return solicitudes.get(usuario);
  }
}
