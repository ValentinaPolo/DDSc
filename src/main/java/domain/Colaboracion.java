package domain;

import java.util.Date;

public abstract class Colaboracion {
    private String fechaColaboracion;
    private String cantidad;
    private Colaborador colaborador;

    public String getFechaColaboracion() {
        return fechaColaboracion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }
}
