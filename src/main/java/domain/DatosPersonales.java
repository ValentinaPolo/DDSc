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

}
