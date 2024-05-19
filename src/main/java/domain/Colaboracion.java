package domain;

import java.util.Date;

public abstract class Colaboracion {
    private String tipoDoc;
    private String nroDocumento;
    private String nombre;
    private String apellido;
    private String mail;
    private String fechaColaboracion;
    private String formaColaboracion;
    private String cantidad;
    private Usuario usuario;

    public Colaboracion(String data[]) {
        this.tipoDoc= data[0];
        this.nroDocumento = data[1];
        this.nombre = data[2];
        this.apellido = data[3];
        this.mail = data[4];
        this.fechaColaboracion = data[5];
        this.formaColaboracion = data[6];
        this.cantidad = data[7];
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
