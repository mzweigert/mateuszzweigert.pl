package pl.mateuszzweigert.site.model;

import org.hibernate.validator.constraints.Email;
import pl.mateuszzweigert.site.common.MessagesValidate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Mail {

    @NotNull(message = MessagesValidate.NOT_BLANK_MESSAGE)
    @Size(min = 3, max = 15, message = MessagesValidate.SIZE_MESSAGE)
    private String name;
    @NotNull(message = MessagesValidate.NOT_BLANK_MESSAGE)
    @Size(min = 3, max = 35, message = MessagesValidate.SIZE_MESSAGE)
    private String subject;
    @NotNull(message = MessagesValidate.NOT_BLANK_MESSAGE)
    @Size(min = 3, max = 35, message = MessagesValidate.SIZE_MESSAGE)
    @Email(message = MessagesValidate.EMAIL_VALIDATE)
    private String email;
    @NotNull(message = MessagesValidate.NOT_BLANK_MESSAGE)
    @Size(min = 10, max = 800, message = MessagesValidate.SIZE_MESSAGE)
    private String content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
