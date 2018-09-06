package pl.mateuszzweigert.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import pl.mateuszzweigert.site.model.Mail;
import pl.mateuszzweigert.site.support.web.MailSender;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.common.Views;

import javax.validation.Valid;

@Controller
public class ContactController {

    private final MailSender mailSender;

    @Autowired
    public ContactController(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @GetMapping(value = Routes.CONTACT)
    public String contactGet(Model model) {
        model.addAttribute(new Mail());
        return Views.CONTACT_VIEW;
    }

    @PostMapping(value = Routes.CONTACT)
    public ModelAndView contactPost(@Valid @ModelAttribute("mail") Mail mail,
                                    BindingResult bindingResult, ModelMap model) {
        ModelAndView modelAndView = new ModelAndView(Views.CONTACT_VIEW, model);
        if (bindingResult.hasErrors()) {
            modelAndView.setStatus(HttpStatus.NOT_ACCEPTABLE);
            return modelAndView;
        }

        boolean sendResult = mailSender.sendMail(mail);
        model.put("sendResult", sendResult);
        modelAndView.setStatus(sendResult ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
        return modelAndView;
    }

}
