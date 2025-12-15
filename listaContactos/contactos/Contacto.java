package listaContactos.contactos;

import java.io.Serializable;
import java.util.LinkedList;

import listaContactos.persistencia.AlmacenContactos;

public class Contacto implements Serializable{
  private String nombre;
  private String telefono;
  private String eMail;
  private String url;

  public Contacto(String nombre, String telefono, String eMail, String url) {
    this.nombre = nombre;
    this.telefono = telefono;
    this.eMail = eMail;
    this.url = url;
  }

  public String getNombre() {
    return this.nombre;
  }

  public String getTelefono() {
    return this.telefono;
  }

  public String getEMail() {
    return this.eMail;
  }

  public String getUrl() {
    return this.url;
  }

  public String verDetalles() {
    return this.toString();
  }


  @Override
  public String toString() {
      String res = "Contacto: " + "\n" + this.nombre + "\n";
      res += "Telefono: " + this.telefono + "\n";
      res += "Email: " + this.eMail + "\n";
      res += "URL: " + this.url + "\n\n";
      return res;

  }

  public static String cargarContactos() {
    return AlmacenContactos.cargarContactos();
  }

  public static String respaldarContactos() {
    return AlmacenContactos.respaldarContactos();
  }

  public static LinkedList<Contacto> getContactos(String usuario) {
    return AlmacenContactos.getContactos(usuario);
  }

  public static void agregarContacto(String usuario, Contacto contacto) {
    AlmacenContactos.agregarContacto(usuario, contacto);
  }
}
