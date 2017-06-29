package pl.mateuszzweigert.site.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Mail {

    @NotNull
    @Size(min = 3)
    private String name;
    @NotNull
    @Size(min = 3)
    private String subject;
    @NotNull
    @Size(min = 3)
    private String email;
    @NotNull
    @Size(min = 10)
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
