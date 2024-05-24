package colaboraciones;

import domain.Colaborador;

import java.util.Date;

public class DonacionDeDinero  {
    private Date fechaColaboracion;
    private Integer cantidad;
    private Colaborador colaborador;

    public Date getFechaColaboracion() {
        return fechaColaboracion;
    }

    public  Integer getCantidad() {
        return cantidad;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public DonacionDeDinero(Date fechaColaboracion, Integer cantidad, Colaborador colaborador) {
        this.fechaColaboracion = fechaColaboracion;
        this.cantidad = cantidad;
        this.colaborador = colaborador;
    }
}
