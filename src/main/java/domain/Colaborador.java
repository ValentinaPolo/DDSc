package domain;

import java.util.ArrayList;

public class Colaborador {
    private DatosPersonales datosPersonales;
    private Usuario usuario;
    private ArrayList<Colaboracion> colaboraciones = new ArrayList<>();

    public Colaborador(DatosPersonales datosPersonales, Usuario usuario) {
        this.datosPersonales = datosPersonales;
        this.usuario = usuario;
    }

    public ArrayList<Colaboracion> getColaboraciones() {
        return colaboraciones;
    }

    public void agregarColaboracion(Colaboracion colaboracion)
    {
        this.colaboraciones.add(colaboracion);
    }

    public DatosPersonales getDatosPersonales() {
        return datosPersonales;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
