package pl.mateuszzweigert.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.common.Views;

@Controller
public class ExperienceController {

    @GetMapping(value = Routes.EXPERIENCE)
    public String experience() {
        return Views.EXPERIENCE_VIEW;
    }

}
