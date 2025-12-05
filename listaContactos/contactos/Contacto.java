package listaContactos.contactos;

public class Contacto {
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
}
