package domain;

import java.util.ArrayList;

public class Colaborador {
    private DatosPersonales datosPersonales;
    private Usuario usuario;


    public Colaborador(DatosPersonales datosPersonales, Usuario usuario) {
        this.datosPersonales = datosPersonales;
        this.usuario = usuario;
    }



    public DatosPersonales getDatosPersonales() {
        return datosPersonales;
    }

    public Usuario getUsuario() {
        return usuario;
    }

}
