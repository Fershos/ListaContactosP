package listaContactos.contactos;

import java.io.Serializable;

public class SolicitudImporte implements Serializable {
  private String solicitante;

  public SolicitudImporte(String solicitante) {
    this.solicitante = solicitante;
  }

  public String getUsuarioSolicitante() {
    return solicitante;
  }
}
