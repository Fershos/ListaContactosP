package listaContactos.contactos;

import java.io.Serializable;
import java.util.LinkedList;
import listaContactos.persistencia.Cifrador;
import listaContactos.persistencia.AlmacenUsuarios;

public class Usuario implements Serializable {
  private String nombre;
  private String usuario;
  private String password;
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

  public void registrar() {
    AlmacenUsuarios.guardarUsuario(this);
  }

  public String getUsuario() {
    return this.usuario;
  }

  public String getPassword() {
    return this.usuario;
  }

  public LinkedList<Contacto> getListaContactos() {
    return this.contactos;
  }

  public static LinkedList<Usuario> getUsuarios() {
    return AlmacenUsuarios.getUsuarios();
  }

  public static Usuario logIn(String usuario, String password) {
    for(Usuario u : Usuario.getUsuarios())
      if(u.getUsuario().equals(usuario) && u.getPassword().equals(Cifrador.hashear(password)))
        return u;

    return new Usuario(null, null, null, null);
  }

  public Contacto getContactoByNombre(String nombre) {
      for(Contacto contacto : this.contactos){
        // TODO Hacer pruebas para saber si crashea
          if(contacto.getNombre().equals(nombre)){
              return contacto;
          }
      }
      return new Contacto(null, null, null, null);
  }

  public boolean contactoExiste(Contacto contacto) {
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
    boolean b = false;
    try {
      b = this.usuario.isBlank();
    } catch(Exception e) {
      b = true;
    }
    return !b;
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

    eliminarSolicitud(solicitante);
  }

  public void eliminarSolicitud(Usuario solicitante) {
    // TODO Implementar esta
  }

  public static Usuario getUsuarioByUser(String user) {
      for(Usuario usuario : AlmacenUsuarios.getUsuarios()){
        // TODO Hacer pruebas para saber si crashea
          if(usuario.usuario.equals(user)){
              return usuario;
          }
      }
    return new Usuario(user, user, user, user);
  }

  public static void respaldarListaUsuarios() {
    AlmacenUsuarios.respaldarUsuarios();
  }
}
