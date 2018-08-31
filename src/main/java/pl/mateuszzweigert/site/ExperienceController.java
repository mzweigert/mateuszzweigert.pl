package pl.mateuszzweigert.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.common.Views;

@Controller
public class ExperienceController {

    @RequestMapping(value = Routes.EXPERIENCE, method = RequestMethod.GET)
    public String experience() {
        return Views.EXPERIENCE_VIEW;
    }

}
