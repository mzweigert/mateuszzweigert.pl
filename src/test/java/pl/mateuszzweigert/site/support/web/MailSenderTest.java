package pl.mateuszzweigert.site.support.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.junit4.SpringRunner;
import pl.mateuszzweigert.site.model.Mail;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSenderTest {

    @Autowired
    private MailSender mailSender;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    public void sendMailSuccess() {
        //GIVEN
        Mail mail = new Mail();

        //WHEN
        boolean result = mailSender.sendMail(mail);

        //THEN
        assertThat(result).isTrue();
    }


    @Test
    public void sendMailFail() {
        //GIVEN
        Mail mail = new Mail();

        //WHEN
        doThrow(MailSendException.class).when(javaMailSender).send(any(SimpleMailMessage.class));
        boolean result = mailSender.sendMail(mail);

        //THEN
        assertThat(result).isFalse();
    }
}