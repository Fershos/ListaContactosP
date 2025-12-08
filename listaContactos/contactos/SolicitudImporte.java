package listaContactos.contactos;

import java.io.Serializable;

public class SolicitudImporte implements Serializable {
  private String solicitante;

  public SolicitudImporte(String solicitante) {
    this.solicitante = solicitante;
  }

  public void aceptar(Usuario usuario){
    for(Contacto contacto : Usuario.getUsuarioByUser(solicitante).getListaContactos()) {
      usuario.registrarContacto(new Contacto(contacto.getNombre() + "(" + usuario.getUsuario() + ")",
                                            contacto.getTelefono(),
                                            contacto.getEMail(),
                                            contacto.getUrl()));
    }

    usuario.eliminarSolicitud(solicitante);
  }

  public String getUsuarioSolicitante() {
    return solicitante;
  }

  public void declinar(Usuario usuario) {
    usuario.eliminarSolicitud(solicitante);
  }
}
