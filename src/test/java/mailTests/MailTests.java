package mailTests;

import colaboraciones.DonacionDeDinero;
import domain.Colaboracion;
import domain.Colaborador;
import domain.DatosPersonales;
import domain.EnviadorMail;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;

public class MailTests {
        public void testEviarMail(){
        DatosPersonales datos = new DatosPersonales("DNI", "12345678", "Juan", "Perez", "vpolocurbelo@frba.utn.edu.ar");
        Colaborador colaborador = new Colaborador(datos, null);
        enviarCorreo(colaborador);
    }


    private void enviarCorreo (Colaborador colaborador){
        try {
            Email email = EnviadorMail.conectaEmail();
            email.setSubject("Agradecimiento por colaboracion");
            email.setMsg("Estimado" +
                    "Gracias por tu colaboracion,\n\n Te creamos una cuenta. Tus credenciales de acceso son:" +
                    "Usuario: "
                    + "Contrasenia = " +
                    "Por favor ingrese al sistema y verifique los datos");
            email.addTo(colaborador.getDatosPersonales().getMail());
            email.send();

        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}