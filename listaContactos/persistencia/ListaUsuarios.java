package listaContactos.persistencia;

import java.util.LinkedList;
import listaContactos.contactos.Usuario;

public class ListaUsuarios {
  private static LinkedList<Usuario> usuarios = new LinkedList<>();

  public static LinkedList<Usuario> getUsuarios() {
    return usuarios;
  }
}
