package SendEmail;
//import java.time.LocalDate;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.Authenticator;

//import jakarta.websocket.Session;


public class SendEmail {
	public void sendEmail(String subject, String to, String from,String htmlContent) throws MessagingException {
        // Gmail's SMTP server address

		String host = "smtp.gmail.com";

        // Set system properties
        Properties properties = System.getProperties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.debug", "true");
        properties.put("mail.smtp.ssl.protocols","TLSv1.2");
        properties.put("username","railway.reservationproject12@gmail.com");
        properties.put("password","anep dilv ltes qhaq");

        // Get session with authentication
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "anep dilv ltes qhaq"); // Replace with your actual password
            }
        });

        session.setDebug(true);  // Enable debug for troubleshooting

        // Create MimeMessage object
        MimeMessage mm = new MimeMessage(session);

        try {
            // Set sender's email address
            mm.setFrom(new InternetAddress(from));

            // Add recipient
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            mm.setContent(message);
            // Set subject and message content

            MimeBodyPart htmlPart = new MimeBodyPart();

            htmlPart.setContent(htmlContent,"text/html");

//            MimeBodyPart textPart = new MimeBodyPart(); textPart.setText(textContent);

            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(htmlPart);

            mm.setContent(multipart);
            mm.setSubject(subject);
//            mm.setText(message);

            // Send the message
            Transport.send(mm);
             System.out.println("Message sent successfully!");

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}


