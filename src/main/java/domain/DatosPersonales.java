package domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter

public class DatosPersonales {
    private String nombre;
    private String apellido;
    private String medioDeContacto;
    private Date fechaNacimiento;
    private String direccion;
    private String mail;

    public DatosPersonales(String tipoDoc, String documento, String nombre, String apellido, String mail) {
    }
}
