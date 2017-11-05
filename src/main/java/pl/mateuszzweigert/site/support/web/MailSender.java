package pl.mateuszzweigert.site.support.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.mateuszzweigert.site.model.Mail;

@Component
public class MailSender {

    @Value("${email.username}")
    private String USERNAME;

    @Autowired
    public JavaMailSender javaMailSender;

    public boolean sendMail(Mail mail) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mail.getEmail());
        message.setTo(USERNAME);
        message.setSubject(mail.getSubject());
        message.setText(getText(mail));
        try {
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getText(Mail mail) {

        return new StringBuilder()
                .append("You have recieved mail from your page, from :")
                .append(mail.getName())
                .append(", ")
                .append(mail.getEmail())
                .append(". \n")
                .append(mail.getContent())
                .toString();
    }
}
