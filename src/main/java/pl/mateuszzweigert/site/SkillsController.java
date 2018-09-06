package pl.mateuszzweigert.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.common.Views;

@Controller
public class SkillsController {

    @GetMapping(value = Routes.SKILLS)
    public String skills() {
        return Views.SKILLS_VIEW;
    }
}
