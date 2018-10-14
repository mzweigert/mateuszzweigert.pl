package pl.mateuszzweigert.site;

import net.bytebuddy.utility.RandomString;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.model.Mail;
import pl.mateuszzweigert.site.support.web.MailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest extends AbstractContextLoadTest<ContactController> {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MailSender mailSender;

    @Test
    public void contactRoute() throws Exception {
        this.mockMvc.perform(get(Routes.CONTACT))
                .andExpect(status().isOk());
    }

    @Test
    public void givenCorrectMail_whenSendContactFormWithCSRF_thenSuccessSend() throws Exception {
        Mail mail = createMail("test@test", RandomString.make(15),
                RandomString.make(15), RandomString.make(15));

        when(mailSender.sendMail(any())).thenReturn(true);

        this.mockMvc.perform(fillPOSTRequestWithMail(mail)
                .with(csrf()))
                .andExpect(status().isOk());
    }

    @Test
    public void givenIncorrectMail_whenSendContact_thenFailedSend() throws Exception {
        Mail mail = createMail("", "", "", "");

        this.mockMvc.perform(fillPOSTRequestWithMail(mail)
                .with(csrf()))
                .andExpect(status().isNotAcceptable());
    }

    @Test
    public void givenNotWorkingMailer_whenSendContactFormWithCSRF_thenFailedSend() throws Exception {
        Mail mail = createMail("test@test", RandomString.make(15),
                RandomString.make(15), RandomString.make(15));

        when(mailSender.sendMail(any())).thenReturn(false);

        this.mockMvc.perform(fillPOSTRequestWithMail(mail)
                .with(csrf()))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void givenNoAuth_SendContactOutside_thenFailedSend() throws Exception {
        Mail mail = createMail("test@test", RandomString.make(15),
                RandomString.make(15), RandomString.make(15));

        this.mockMvc.perform(fillPOSTRequestWithMail(mail))
                .andExpect(status().isForbidden());
    }

    private Mail createMail(String email, String name, String subject, String content) {
        Mail mail = new Mail();
        mail.setEmail(email);
        mail.setName(name);
        mail.setSubject(subject);
        mail.setContent(content);
        return mail;
    }

    private MockHttpServletRequestBuilder fillPOSTRequestWithMail(Mail mail) {
        return post(Routes.CONTACT)
                .contentType(MediaType.APPLICATION_JSON)
                .param("email", mail.getEmail())
                .param("name", mail.getName())
                .param("subject", mail.getSubject())
                .param("content", mail.getContent());
    }
}