package person.zhao.mail;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    private static String RECIPIENT = "zhao_hongsheng@microad-tech.com";
    private Properties passProps = null;

    public MailSender() {
        readProps();
    }

    private void sendmail(String[] to, String subject, String body) {
        Properties props = System.getProperties();

        // =========== 263 ===============
        // 非加密，使用25 端口
        // HOST and PORT
        // String host = "smtp.263.net";
        // props.put("mail.smtp.host", host);
        // props.put("mail.smtp.port", "25");
        //
        // // USER_NAME and PASSWORD
        // String userName = passProps.getProperty("263.user.name");
        // String password = passProps.getProperty("263.user.pass");
        // props.put("mail.smtp.auth", "true");
        // props.put("mail.smtp.user", userName);
        // props.put("mail.smtp.password", password);

        // =========== qq ===============
        // 加密，使用587端口，具体端口还需要根据smtp服务器来设定
        // HOST and PORT
        String host = "smtp.qq.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", host);

        // USER_NAME and PASSWORD
        String userName = passProps.getProperty("qq.user.name");
        String password = passProps.getProperty("qq.user.pass");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.user", userName);
        props.put("mail.smtp.password", password);

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
            transport.connect(host, userName, password);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (AddressException ae) {
            ae.printStackTrace();
        } catch (MessagingException me) {
            me.printStackTrace();
        }
    }

    private void readProps() {
        passProps = new Properties();
        InputStream in = MailSender.class.getClassLoader().getResourceAsStream("pass.properties");
        try {
            passProps.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Error, when read properties file !");
        }
    }

    public void run() {
        String[] to = {RECIPIENT};
        String subject = "Java send mail example";
        String body = "Welcome to JavaMail!";
        sendmail(to, subject, body);
    }

    public static void main(String[] args) {
        new MailSender().run();
    }
}
