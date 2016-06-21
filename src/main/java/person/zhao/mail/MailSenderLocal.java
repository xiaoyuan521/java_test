package person.zhao.mail;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 连接本地的127.0.0.1的smtp（25端口）进行邮件发送 无需密码
 * 
 * @author zhao_hongsheng
 */
public class MailSenderLocal {

    private static String RECIPIENT = "zhao_hongsheng@microad-tech.com";

    public MailSenderLocal() {
    }

    private void sendmail(String[] to, String subject, String body) {
        String userName = "zhao_hongsheng@microad-tech.com";

        Properties props = System.getProperties();

        // HOST and PORT
        String host = "127.0.0.1";
        props.put("mail.smtp.port", "25");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "false");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
            // set from
            message.setFrom(new InternetAddress(userName));

            // set to
            InternetAddress[] toAddress = new InternetAddress[to.length];
            for (int i = 0; i < to.length; i++) {
                toAddress[i] = new InternetAddress(to[i]);
            }
            for (int i = 0; i < toAddress.length; i++) {
                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
            }

            // subject
            message.setSubject(subject);
            // body
            message.setText(body);

            // send the mail
            Transport transport = session.getTransport("smtp");
            transport.connect(host, userName);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    public void run() {
        String[] to = {RECIPIENT};
        String subject = "Java send mail example";
        String body = "Welcome to JavaMail!";
        sendmail(to, subject, body);
    }

    public static void main(String[] args) {
        new MailSenderLocal().run();
    }
}
