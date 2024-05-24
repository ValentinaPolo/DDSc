package colaboraciones;

import domain.Colaborador;
import lombok.Getter;

import java.util.Date;
@Getter
public class EntregarTarjeta {
    private Date fechaColaboracion;
    private Integer cantidad;
    private Colaborador colaborador;

    public EntregarTarjeta(Date fechaColaboracion, Integer cantidad, Colaborador colaborador) {
        this.fechaColaboracion = fechaColaboracion;
        this.cantidad = cantidad;
        this.colaborador = colaborador;
    }
}
