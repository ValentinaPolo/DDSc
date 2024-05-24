package colaboraciones;

import domain.Colaborador;
import lombok.Getter;

import java.util.Date;

@Getter
public class DonacionVianda {
    private Date fechaColaboracion;
    private Integer cantidad;
    private Colaborador colaborador;
    public DonacionVianda(Date fechaColaboracion, Integer cantidad, Colaborador colaborador) {
        this.fechaColaboracion = fechaColaboracion;
        this.cantidad = cantidad;
        this.colaborador = colaborador;
    }


}
