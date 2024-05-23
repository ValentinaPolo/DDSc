package mailTests;


import domain.EnviadorMail;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;



public class MailTests {

@Test
public void enviarCorreo() {
        try {

            EnviadorMail.enviarConGMail("congelandoelhambre@gmail.com", "Prueba", "Prueba");
            String enviado = "enviado";
            assertEquals("enviado", enviado);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}