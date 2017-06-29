package pl.mateuszzweigert.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mateuszzweigert.site.model.Mail;
import pl.mateuszzweigert.site.support.web.MailSender;
import pl.mateuszzweigert.site.support.web.Routes;
import pl.mateuszzweigert.site.support.web.Views;

import javax.validation.Valid;

@Controller
public class ContactController {

    @Autowired
    private MailSender mailSender;

    @RequestMapping(value = Routes.CONTACT, method = RequestMethod.GET)
    public String contactGet(Model model) {
        model.addAttribute(new Mail());
        return Views.CONTACT_VIEW;
    }

    @RequestMapping(value = Routes.CONTACT, method = RequestMethod.POST)
    public String contactPost(@Valid @ModelAttribute(value = "mail") Mail mail,
                              BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return Views.CONTACT_VIEW;
        }

        boolean sendResult = mailSender.sendMail(mail);
        model.addAttribute("sendResult", sendResult);
        return Views.CONTACT_VIEW;
    }

}
