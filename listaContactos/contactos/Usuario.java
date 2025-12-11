package listaContactos.contactos;

import java.io.Serializable;
import java.util.LinkedList;
import listaContactos.persistencia.Cifrador;
import listaContactos.persistencia.AlmacenUsuarios;

public class Usuario implements Serializable {
  private String nombre;
  private String usuario;
  private byte[] password;
  private String eMail;
  private LinkedList<Contacto> contactos;
  private LinkedList<SolicitudImporte> solicitudes;

  public Usuario(String nombre, String usuario, String password, String eMail) {
    this.nombre = nombre;
    this.usuario = usuario;
    this.password = Cifrador.hashear(password);
    this.eMail = eMail;
    this.contactos = new LinkedList<>();
    this.solicitudes = new LinkedList<>();
  }

  public Usuario(String nombre, String usuario, byte[] password, String eMail) {
    this.nombre = nombre;
    this.usuario = usuario;
    this.password = password;
    this.eMail = eMail;
    this.contactos = new LinkedList<>();
    this.solicitudes = new LinkedList<>();
  }

  public void registrar() {
    AlmacenUsuarios.guardarUsuario(this);
  }

  public String getUsuario() {
    return this.usuario;
  }

  public String getNombre() {
    return this.nombre;
  }

  public byte[] getPassword() {
    return this.password;
  }

  public String getEMail() {
    return this.eMail;
  }

  public LinkedList<Contacto> getListaContactos() {
    return this.contactos;
  }

  public static LinkedList<Usuario> getUsuarios() {
    return AlmacenUsuarios.getUsuarios();
  }

  public static Usuario logIn(String usuario, String password) {
    byte[] pass = Cifrador.hashear(password);
    for(Usuario u : Usuario.getUsuarios())
      if(u.usuario.equals(usuario) && Cifrador.sonMismoHash(u.getPassword(), pass))
        return u;

    return null;
  }

  public Contacto getContactoByNombre(String nombre) {
      for(Contacto contacto : this.contactos){
        // TODO Hacer pruebas para saber si crashea
          if(contacto.getNombre().equals(nombre)){
              return contacto;
          }
      }
      return null;
  }

  public boolean contactoExiste(Contacto contacto) {
    if (contacto == null) {
      return false;
    }

    for(Contacto cont : contactos){
      // TODO Hacer pruebas para saber si crashea
      if(cont.getNombre().equals(contacto.getNombre())){
        return true;
      }
    }
    return false;
  }
  public boolean registrarContacto(Contacto contacto) {
        if(contactoExiste(contacto)) {
            return false;
        }

        this.contactos.push(contacto);
        return true;
  }

  public void agregarSolicitud(SolicitudImporte solicitud) {
    this.solicitudes.push(solicitud);
  }

  public boolean esValido() {
    return usuario != null && !usuario.isBlank();
  }

  public LinkedList<SolicitudImporte> getSolicitudes() {
    return solicitudes;
  }

  public void aceptarSolicitud(Usuario solicitante) {
    for(Contacto contacto : solicitante.getListaContactos()) {
      registrarContacto(new Contacto(contacto.getNombre() + "(" + solicitante.getUsuario() + ")",
                                            contacto.getTelefono(),
                                            contacto.getEMail(),
                                            contacto.getUrl()));
    }

    eliminarSolicitud(solicitante.getUsuario());
  }

  public void eliminarSolicitud(String solicitante) {
    // TODO Hacer pruebas
    for(SolicitudImporte solicitud : solicitudes)
      if(solicitud.getUsuarioSolicitante().equals(solicitante))
        solicitudes.remove(solicitud);
  }

  public static Usuario getUsuarioByUser(String user) {
      for(Usuario usuario : AlmacenUsuarios.getUsuarios())
        // TODO Hacer pruebas para saber si crashea
          if(usuario.usuario.equals(user))
              return usuario;

    return null;
  }

  public static String respaldarListaUsuarios() {
    return AlmacenUsuarios.respaldarUsuarios();
  }

  public static String cargarListaUsuarios() {
    return AlmacenUsuarios.cargarUsuarios();
  }
}
