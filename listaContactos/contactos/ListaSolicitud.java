package listaContactos.contactos;

import java.util.LinkedList;

public class ListaSolicitud {
  private static LinkedList<SolicitudExporta> solicitudes = new LinkedList<>();

  public static void agregarSolicitud(SolicitudExporta solicitud) {
    solicitudes.add(solicitud);
  }

  public static void eliminarSolicitud(Usuario solicitante, Usuario receptor) {
    // TODO Hacer la implementacion
  }
}
