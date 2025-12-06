package listaContactos.ui;

import java.util.Scanner;

import listaContactos.contactos.Contacto;
import listaContactos.contactos.SolicitudImporte;
import listaContactos.contactos.Usuario;

public class Consola {
  static final int SALIR = 3;

  public static void main(String args[]) {
    int opt = 0;
    Scanner sc = new Scanner(System.in);
    while(opt != SALIR) {
      imprimirMenuPrincipal();
      opt = seleccionarOpcionPrincipal(sc);
    }
  }

  private static void imprimirMenuPrincipal() {
    System.out.println("Bienvenido al Sistema de Contactos\n");
    System.out.println("1)\tRegistrarse");
    System.out.println("2)\tIngresar");
    System.out.println("3)\tSalir");
    System.out.print("Por favor ingresa una opcion: ");
  }

  private static int seleccionarOpcionPrincipal(Scanner sc) {
    int opt = sc.nextInt();
    sc.nextLine();
    switch(opt) {
      case 1:
        registrarUsuario(sc);
        break;

      case 2:
        ingresarComoUsuario(sc);
        break;

      case SALIR:
        System.out.println("Hasta la proxima...");
        guardarDatos();
        break;

      default:
        System.out.println("Opcion no valida\n");
        break;
    }
    return opt;
  }

  public static void registrarUsuario(Scanner sc) {
    System.out.println("**\tRegistro de Usuario\t**");
    System.out.print("Ingresa el nombre: ");
    String nombre = sc.nextLine();
    System.out.print("Ingresa el usuario: ");
    String usuario = sc.nextLine();
    System.out.print("Ingresa el password: ");
    String password = sc.nextLine();
    System.out.print("Ingresa el eMail: ");
    String eMail = sc.nextLine();

    Usuario user = new Usuario(nombre, usuario, password, eMail);
    user.registrar();
  }

  public static void ingresarComoUsuario(Scanner sc) {
    System.out.println("**\tLog In\t**");
    System.out.print("Ingresa el usuario: ");
    String usuario = sc.nextLine();
    System.out.print("Ingresa el password: ");
    String password = sc.nextLine();
    Usuario user = Usuario.logIn(usuario, password);
    if(user.getUsuario().isBlank()) {
      System.out.println("Usuario o contrasenia no validos\n");
      return;
    }
    menuUsuario(user, sc);
  }

  private static void menuUsuario(Usuario user, Scanner sc) {
    System.out.println("Bienvenido al Sistema de Contactos\n");
    System.out.println("Usuario:\t" + user.getUsuario());
    System.out.println("1)\tRegistrar Contacto");
    System.out.println("2)\tVer Contactos");
    System.out.println("3)\tVer detalles de un contacto");
    System.out.println("4)\tCrear solicitud de exportar contactos");
    System.out.println("5)\tVer solicitudes");
    System.out.println("6)\tAceptar solicitud");
    System.out.println("7)\tRechazar solicitud");
    System.out.print("Por favor ingresa una opcion: ");
  }

  public static void registrarContacto(Usuario usuario, Scanner sc) {
    System.out.println("**\tRegistro de Contacto\t**");
    System.out.print("Ingresa el nombre: ");
    String nombre = sc.nextLine();
    System.out.print("Ingresa el telefono: ");
    String telefono = sc.nextLine();
    System.out.print("Ingresa el eMail: ");
    String eMail = sc.nextLine();
    System.out.print("Ingresa el URL(opcional): ");
    String url = sc.nextLine();

    if(usuario.registrarContacto(new Contacto(nombre, telefono, eMail, url))){

        usuario.registrarContacto(new Contacto(nombre, telefono, eMail, url));
        System.out.println("Contacto registrado exitosamente.\n");
    }else {
        System.out.println("Nombre del contacto ocupado.\n");
    }
  }

  public static void verContactos(Usuario user) {
    System.out.println("Lista de contactos:");

    for(Contacto contacto : user.getListaContactos()){
      System.out.println(contacto.getNombre());
    }
  }

  public static void verDetallesContacto(Usuario user, Scanner sc) {
    System.out.print("Detalles de un Contacto");
    System.out.print("Ingresa el nombre:");
    String nombre = sc.nextLine();
    Contacto contacto = user.getContactoByNombre(nombre);

    if (contacto != null) {

        System.out.println("Contacto encontrado: " + contacto.verDetalles());
    }else  {
      System.out.println("Contacto no encontrado\n");
    }

  }

  public static void crearSolicitudExportarContacto(Usuario solicitante, Scanner sc) {
    // NOTE Esto deberia estar en otro metodo, tal vez ser una opcion
    System.out.println("Usuarios diponibles para asignar la solicitud");
    for(Usuario usuario : Usuario.getUsuarios()) {
      System.out.println(usuario.getUsuario());
    }

    System.out.print("Elige el usuario: ");
    String res = sc.nextLine();
    Usuario receptor = Usuario.getUsuarioByUser(res);
    // TODO Esto deberia ser un metodo
    if(receptor.getUsuario().isBlank()) {
      System.out.println("Usuario no encontrado\n");
      return;
    }
    receptor.agregarSolicitud(new SolicitudImporte(solicitante));
  }

  public static void aceptarSolicitud(Usuario usuario, Scanner sc) {
    System.out.println("Que solicitud desea aceptar? ");
    System.out.print("Ingresa el usuario: ");
    String user = sc.nextLine();
    Usuario solicitante = Usuario.getUsuarioByUser(user);
    // TODO Esto deberia ser un metodo
    if(solicitante.getUsuario().isBlank()) {
      System.out.println("Usuario no valido");
      return;
    }

    for(Contacto contacto : solicitante.getListaContactos()) {
      usuario.registrarContacto(new Contacto(contacto.getNombre() + "(" + usuario.getUsuario() + ")",
                                            contacto.getTelefono(),
                                            contacto.getEMail(),
                                            contacto.getUrl()));
    }

    usuario.eliminarSolicitud(solicitante);
  }

  public static void declinarSolicitud(Usuario usuario, Scanner sc) {
    System.out.println("Que solicitud desea rechazar? ");
    System.out.print("Ingresa el usuario: ");
    String user = sc.nextLine();
    Usuario solicitante = Usuario.getUsuarioByUser(user);
    // TODO Esto deberia ser un metodo
    if(solicitante.getUsuario().isBlank()) {
      System.out.println("Usuario no valido");
      return;
    }

    usuario.eliminarSolicitud(solicitante);
  }

  private static void guardarDatos() {

  }
}
