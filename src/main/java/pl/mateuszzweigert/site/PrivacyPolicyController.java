package pl.mateuszzweigert.site;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.common.Views;
import pl.mateuszzweigert.site.model.PrivacyPolicyApp;


@Controller
public class PrivacyPolicyController {

    @GetMapping(Routes.PRIVACY_POLICY + "/{appName}")
    public ModelAndView privacyPolicy(@PathVariable("appName") String appName, ModelMap model) {
        ModelAndView modelAndView;
        PrivacyPolicyApp privacyPolicy = PrivacyPolicyApp.of(appName);
        if (privacyPolicy != null) {
            modelAndView = new ModelAndView(Views.PRIVACY_POLICY_VIEW, model);
            model.addAttribute("privacyPolicy", privacyPolicy);
            modelAndView.setStatus(HttpStatus.OK);
        } else {
            modelAndView = new ModelAndView("forward:/" + Views.PRIVACY_POLICY_VIEW, model);
            modelAndView.setStatus(HttpStatus.NOT_FOUND);
        }
        return modelAndView;
    }

}
