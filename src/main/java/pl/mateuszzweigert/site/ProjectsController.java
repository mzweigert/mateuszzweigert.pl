package pl.mateuszzweigert.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mateuszzweigert.site.support.web.Routes;

import java.security.Principal;

@Controller
public class ProjectsController {

    @RequestMapping(value = Routes.PROJECTS, method = RequestMethod.GET)
    public String projects() {
        return "projects";
    }
}
