package pl.mateuszzweigert.site.projects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class ProjectsController {

    @RequestMapping(value = "/projects", method = RequestMethod.GET)
    public String index() {
        return "projects/projects";
    }
}
