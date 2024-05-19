package domain;

import lombok.Getter;

@Getter
public class Usuario {
    private String nombreUsuario;
    private String nombre;
    private String contrasenia;



   private String generarContrasenia(){
        //TODO
       return null;
   }

    public Usuario(String nombreUsuario, String nombre) {
        this.nombreUsuario = nombreUsuario;
        this.nombre =nombre;
        this.contrasenia = generarContrasenia();

    }
}
