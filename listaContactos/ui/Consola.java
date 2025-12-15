package listaContactos.ui;

import java.util.Scanner;

import listaContactos.contactos.Contacto;
import listaContactos.contactos.SolicitudImporte;
import listaContactos.contactos.Usuario;

public class Consola {
  static final int SALIR_PRINCIPAL = 3;
  static final int SALIR_USUARIO = 9;

  public static void main(String args[]) {
    int opt = 0;
    Scanner sc = new Scanner(System.in);
    System.out.println(Usuario.cargarListaUsuarios());
    System.out.println(Usuario.cargarListaContactos());
    System.out.println(Usuario.cargarListaSolicitudes());
    while(opt != SALIR_PRINCIPAL) {
      imprimirMenuPrincipal();
      opt = seleccionarOpcionPrincipal(sc);
    }
  }

  private static int validarEntradaEntero(Scanner sc) {
    while(true) {
      try {
        int opcion = sc.nextInt();
        sc.nextLine();
        return opcion;
      } catch (Exception e) {
        System.out.println("ERROR: Debes ingresar un numero");
        System.out.print("Por favor ingresa una opcion: ");
        sc.nextLine();
      }
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
    int opt = validarEntradaEntero(sc); 
    switch(opt) {
      case 1:
        registrarUsuario(sc);
        Usuario.respaldarListaUsuarios();
        break;

      case 2:
        ingresarComoUsuario(sc);
        break;

      case SALIR_PRINCIPAL:
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

    if(sonCamposValidosUsuario(nombre, usuario, password, eMail)){
      Usuario user = new Usuario(nombre, usuario, password, eMail);
      user.registrar();
      System.out.println("\nRegistro Exitoso\n");
    }else{
      System.out.println("\nRegistro incompleto, todos los campos deben llenarse\n");
    }

  }

  public static void ingresarComoUsuario(Scanner sc) {
    System.out.println("**\tLog In\t**");
    System.out.print("Ingresa el usuario: ");
    String usuario = sc.nextLine();
    System.out.print("Ingresa el password: ");
    String password = sc.nextLine();
    Usuario user = Usuario.logIn(usuario, password);
    if(user == null) {
      System.out.println("\n !!!Usuario o contrasenia no validos¡¡¡\n");
      return;
    }
    int opt = 0;
    while(opt != SALIR_USUARIO) {
      menuUsuario(user);
      opt = seleccionarOpcionUsuario(user, sc);
    }
  }

  private static void menuUsuario(Usuario user) {
    System.out.println("Bienvenido al Sistema de Contactos\n");
    System.out.println("Usuario:\t" + user.getUsuario());
    System.out.println("1)\tRegistrar Contacto");
    System.out.println("2)\tVer Contactos");
    System.out.println("3)\tVer detalles de un contacto");
    System.out.println("4)\tVer usuarios disponibles para enviar solicitud de exportacion de contactos");
    System.out.println("5)\tCrear solicitud de exportar contactos");
    System.out.println("6)\tVer solicitudes");
    System.out.println("7)\tAceptar solicitud");
    System.out.println("8)\tRechazar solicitud");
    System.out.println("9)\tSalir");
    System.out.print("Por favor ingresa una opcion: ");
  }

  private static int seleccionarOpcionUsuario(Usuario usuario, Scanner sc) {
    int opt = validarEntradaEntero(sc);
    switch(opt) {
      case 1:
        registrarContacto(usuario, sc);
        Usuario.respaldarListaContactos();
        break;

      case 2:
        verContactos(usuario);
        break;

      case 3:
        verDetallesContacto(usuario, sc);
        break;

      case 4:
        verUsuariosDisponiblesSolicitud(usuario);
        break;

      case 5:
        crearSolicitudExportarContacto(usuario, sc);
        Usuario.respaldarListaSolicitudes();
        break;

      case 6:
        verSolicitudes(usuario);
        break;

      case 7:
        aceptarSolicitud(usuario, sc);
        Usuario.respaldarListaSolicitudes();
        break;

      case 8:
        declinarSolicitud(usuario, sc);
        Usuario.respaldarListaSolicitudes();
        break;

      case SALIR_USUARIO:
        System.out.println("Hasta la proxima...");
        break;

      default:
        System.out.println("\nOpcion no valida\n");
        break;
    }
    return opt;
  }

  public static boolean sonCamposValidosContacto(String c1, String c2, String c3){

      return !c1.isEmpty() && !c2.isEmpty() && !c3.isEmpty();
  }

  public static boolean sonCamposValidosUsuario(String c1, String c2, String c3, String c4){

    return !c1.isEmpty() && !c2.isEmpty() && !c3.isEmpty() && !c4.isEmpty();
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

    if(sonCamposValidosContacto(nombre, telefono, eMail))
      if(usuario.registrarContacto(new Contacto(nombre, telefono, eMail, url)))
        System.out.println("\nContacto registrado exitosamente.\n");
      else
        System.out.println("\nNombre del contacto ocupado.\n");
    else
      System.out.println("\nERROR: Los datos nombre, telefono y eMail no pueden estar vacios.\nContaco no registrado.\n");

  }

  public static void verContactos(Usuario user) {
    System.out.println("\nLista de contactos:");

    try {
      for(Contacto contacto :  user.getListaContactos())
        System.out.println(contacto.getNombre());
      System.out.println("\n");
    } catch(NullPointerException e) {
      System.out.println("¡¡No tienes contactos!!\n");
    }
  }

  public static void verDetallesContacto(Usuario user, Scanner sc) {
    System.out.print("Detalles de un Contacto\n");
    System.out.print("Ingresa el nombre: ");
    String nombre = sc.nextLine();
    Contacto contacto = user.getContactoByNombre(nombre);

    if (contacto == null) {
      System.out.println("Contacto no encontrado\n");
      return;
    }

    System.out.println("Contacto encontrado: \n\n" + contacto.verDetalles());

  }

  public static void verUsuariosDisponiblesSolicitud(Usuario user) {
    System.out.println("Usuarios disponibles para asigna la solicitud");
    for(Usuario usuario : Usuario.getUsuarios()) {
      if(!usuario.getUsuario().equals(user.getUsuario()))
        System.out.println(usuario.getUsuario());
    }
    System.out.println("\n");
  }

  public static void verSolicitudes(Usuario usuario) {
      System.out.println("Solicitudes disponibles:");
      try {
        for(SolicitudImporte solicitud : usuario.getSolicitudes()) {
          System.out.println(solicitud.getUsuarioSolicitante());
        }
        System.out.println("\n");
      } catch(NullPointerException e) {
        System.out.println("¡¡No tienes solicitudes!!\n");
      }
  }

  public static void crearSolicitudExportarContacto(Usuario solicitante, Scanner sc) {
    System.out.print("Elige el usuario: ");
    String res = sc.nextLine();
    if(solicitante.getUsuario().equals(res)){
      System.out.println("Usuario no valido\n");
      return;
    }
    Usuario receptor = Usuario.getUsuarioByUser(res);
    if(receptor == null){
      System.out.println("Usuario no encontrado\n");
      return;
    }
    receptor.agregarSolicitud(new SolicitudImporte(solicitante.getUsuario()));
    System.out.print("Solicitud enviada¡\n\n");
  }

  public static void aceptarSolicitud(Usuario usuario, Scanner sc) {
    System.out.println("Que solicitud desea aceptar? ");
    System.out.print("Ingresa el usuario: ");
    String user = sc.nextLine();
    if (usuario.getUsuario().equals(user)) {
      System.out.println("No puedes aceptar una solicitud de ti mismo\n");
      return;
    }
    Usuario solicitante = Usuario.getUsuarioByUser(user);
    if(solicitante == null) {
      System.out.println("Usuario no valido\n");
      return;
    }
    usuario.aceptarSolicitud(solicitante);
    System.out.println("Solicitud aceptada¡\n");

  }

  public static void declinarSolicitud(Usuario usuario, Scanner sc) {
    System.out.println("Que solicitud desea rechazar? ");
    System.out.print("Ingresa el usuario: ");
    String user = sc.nextLine();
    Usuario solicitante = Usuario.getUsuarioByUser(user);
    if(solicitante == null) {
      System.out.println("Usuario no valido\n");
      return;
    }

    usuario.eliminarSolicitud(solicitante.getUsuario());
    System.out.println("Solicitud declinada¡\n");
  }

  private static void guardarDatos() {
    Usuario.respaldarListaUsuarios();
    Usuario.respaldarListaContactos();
    Usuario.respaldarListaSolicitudes();
  }
}
