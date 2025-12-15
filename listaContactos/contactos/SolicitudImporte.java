package listaContactos.contactos;

import java.io.Serializable;
import java.util.LinkedList;

import listaContactos.persistencia.AlmacenSolicitudes;

public class SolicitudImporte implements Serializable {
  private String solicitante;

  public SolicitudImporte(String solicitante) {
    this.solicitante = solicitante;
  }

  public String getUsuarioSolicitante() {
    return solicitante;
  }

  public static String cargarSolicitudes() {
    return AlmacenSolicitudes.cargarSolicitudes();
  }

  public static String respaldarSolicitudes() {
    return AlmacenSolicitudes.respaldarSolicitudes();
  }

  public static void agregarSolicitud(String usuario, SolicitudImporte solicitud) {
    AlmacenSolicitudes.agregarSolicitud(usuario, solicitud);
  }

  public static LinkedList<SolicitudImporte> getSolicitudes(String usuario) {
    return AlmacenSolicitudes.getSolicitudes(usuario);
  }
}
