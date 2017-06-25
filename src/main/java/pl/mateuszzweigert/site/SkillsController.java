package pl.mateuszzweigert.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class SkillsController {

    @RequestMapping(value = "/skills", method = RequestMethod.GET)
    public String projects() {
        return "skills";
    }
}
