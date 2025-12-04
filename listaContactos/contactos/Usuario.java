package listaContactos.contactos;

import java.util.LinkedList;
import listaContactos.persistencia.Cifrador;
import listaContactos.persistencia.ListaUsuarios;

public class Usuario {
  private String nombre;
  private String usuario;
  private String password;
  private String eMail;
  private ListaContactos contactos;

  public Usuario(String nombre, String usuario, String password, String eMail) {
    this.nombre = nombre;
    this.usuario = usuario;
    this.password = Cifrador.hashear(password);
    this.eMail = eMail;
    this.contactos = new ListaContactos();
  }

  public void registrar() {

  }

  public String getUsuario() {
    return this.usuario;
  }

  public String getPassword() {
    return this.usuario;
  }

  public LinkedList<Contacto> getListaContactos() {
    return this.contactos.getContactos();
  }

  public static LinkedList<Usuario> getUsuarios() {
    return ListaUsuarios.getUsuarios();
  }

  public static Usuario logIn(String usuario, String password) {
    for(Usuario u : Usuario.getUsuarios())
      if(u.getUsuario().equals(usuario) && u.getPassword().equals(Cifrador.hashear(password)))
        return u;

    return new Usuario(null, null, null, null);
  }

  public Contacto getContactoByNombre(String nombre) {
    return new Contacto(null, null, null, null);
  }

  public void registrarContacto(Contacto contacto) {
    this.contactos.agregarContacto(contacto);
  }

  public static Usuario getUsuarioByUser(String user) {
    // TODO Implementar esto
    return new Usuario(user, user, user, user);
  }
}
