package domain;

import java.util.Properties;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
public class EnviadorMail {
    private static final String HOSTNAME = "smtp.gmail.com";
    private static final String USERNAME = "xxxx@gmail.com";
    private static final String PASSWORD = "xxxxxx";
    private static final String EMAILORIGEM = "xxx@gmail.com";

    public static Email conectaEmail() throws EmailException {
        Email email = new SimpleEmail();
        email.setHostName(HOSTNAME);
        email.setSmtpPort(587);
        email.setAuthentication(USERNAME, PASSWORD);
        email.setStartTLSEnabled(true);
        email.setFrom(EMAILORIGEM);

        return email;
    }

}
