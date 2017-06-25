package pl.mateuszzweigert.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mateuszzweigert.site.model.LocaleRoutes;
import pl.mateuszzweigert.site.model.Mail;
import pl.mateuszzweigert.site.support.web.MailSender;
import pl.mateuszzweigert.site.support.web.Routes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;

@Controller
public class ContactController {

    @Autowired
    private MailSender mailSender;

    @RequestMapping(value = Routes.CONTACT, method = RequestMethod.GET)
    public String contactGet(Locale locale, Model model, HttpServletRequest request) {
        model.addAttribute(new Mail());
        model.addAttribute("routes", new LocaleRoutes(locale, request.getRequestURI()));
        return "contact";
    }

    @RequestMapping(value = "/contact", method = RequestMethod.POST)
    public String contactPost(@Valid @ModelAttribute(value = "mail") Mail mail,
                              BindingResult bindingResult,
                              Locale locale, Model model, HttpServletRequest request) {

        model.addAttribute("routes", new LocaleRoutes(locale, request.getRequestURI()));
        if (bindingResult.hasErrors()){
            return "contact";
        }

        boolean sendResult = mailSender.sendMail(mail);
        model.addAttribute("sendResult", sendResult);
        return "contact";
    }

}
