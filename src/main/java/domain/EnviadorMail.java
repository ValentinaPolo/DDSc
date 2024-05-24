package domain;

import java.util.Properties;

import jakarta.mail.*;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

public class EnviadorMail {
    static String remitente = "congelandoelhambre@gmail.com";


    static String usuario = "congelandoelhambre@gmail.com";
    static String clave = "fwqw awii ukmx ukwz";

    public static void enviarConGMail(String destinatario, String asunto, String cuerpo) {

        try {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(remitente));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
        message.setSubject(asunto);
        message.setText(cuerpo);

        Transport.send(message);


    } catch (MessagingException e) {
        throw new RuntimeException(e);
    }
}
    private static Properties getGmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }

    private static Session getEmailSession() {
        return Session.getInstance(getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remitente, clave);
            }
        });
    }
}
