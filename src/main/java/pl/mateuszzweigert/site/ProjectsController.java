package pl.mateuszzweigert.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.common.Views;

@Controller
public class ProjectsController {

    @GetMapping(value = Routes.PROJECTS)
    public String projects() {
        return Views.PROJECTS_VIEW;
    }
}
