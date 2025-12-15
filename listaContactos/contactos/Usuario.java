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

  public Usuario(String nombre, String usuario, String password, String eMail) {
    this.nombre = nombre;
    this.usuario = usuario;
    this.password = Cifrador.hashear(password);
    this.eMail = eMail;
  }

  public Usuario(String nombre, String usuario, byte[] password, String eMail) {
    this.nombre = nombre;
    this.usuario = usuario;
    this.password = password;
    this.eMail = eMail;
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
    return Contacto.getContactos(this.usuario);
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
    try {
      for(Contacto contacto : getListaContactos()){
        if(contacto.getNombre().equals(nombre)){
          return contacto;
        }
      }
    } catch(NullPointerException e) {
      return null;
    }
    return null;
  }

  public boolean contactoExiste(Contacto contacto) {
    if (contacto == null) {
      return false;
    }

    try {
      for(Contacto cont : getListaContactos()){
        if(cont.getNombre().equals(contacto.getNombre())){
          return true;
        }
      }
    } catch(NullPointerException e) {
      return false;
    }
    return false;
  }
  public boolean registrarContacto(Contacto contacto) {
    if(contactoExiste(contacto))
      return false;
    Contacto.agregarContacto(this.usuario, contacto);
    return true;
  }

  public void agregarSolicitud(SolicitudImporte solicitud) {
    SolicitudImporte.agregarSolicitud(this.usuario, solicitud);
  }

  public LinkedList<SolicitudImporte> getSolicitudes() {
    return SolicitudImporte.getSolicitudes(this.usuario);
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
    for(SolicitudImporte solicitud : SolicitudImporte.getSolicitudes(this.usuario))
      if(solicitud.getUsuarioSolicitante().equals(solicitante))
        SolicitudImporte.getSolicitudes(this.usuario).remove(solicitud);
  }

  public static Usuario getUsuarioByUser(String user) {
      for(Usuario usuario : AlmacenUsuarios.getUsuarios())
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

  public static String respaldarListaContactos() {
    return Contacto.respaldarContactos();
  }

  public static String cargarListaContactos() {
    return Contacto.cargarContactos();
  }

  public static String respaldarListaSolicitudes() {
    return SolicitudImporte.respaldarSolicitudes();
  }

  public static String cargarListaSolicitudes() {
    return SolicitudImporte.cargarSolicitudes();
  }
}
