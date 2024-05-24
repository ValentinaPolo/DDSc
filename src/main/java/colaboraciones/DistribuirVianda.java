package colaboraciones;

import domain.Colaborador;

import java.util.Date;

public class DistribuirVianda {
    private Date fechaColaboracion;
    private Integer cantidad;
    private Colaborador colaborador;
    public DistribuirVianda(Date fechaColaboracion, Integer cantidad, Colaborador colaborador) {
        this.fechaColaboracion = fechaColaboracion;
        this.cantidad = cantidad;
        this.colaborador = colaborador;
    }
}
