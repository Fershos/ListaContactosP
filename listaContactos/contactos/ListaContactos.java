package listaContactos.contactos;

import java.util.LinkedList;

public class ListaContactos {
  private LinkedList<Contacto> contactos;

  public ListaContactos() {
    this.contactos = new LinkedList<>();
  }

  public LinkedList<Contacto> getContactos() {
    return this.contactos;
  }

  public void agregarContacto(Contacto contacto) {
    // TODO verificar si el usuario no existe
    this.contactos.push(contacto);
  }
}
