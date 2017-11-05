package pl.mateuszzweigert.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.common.Views;

@Controller
public class HomeController {

    @RequestMapping(value = {Routes.HOME_1, Routes.HOME_2, Routes.HOME_3}, method = RequestMethod.GET)
    public String home() {
        return Views.HOME_VIEW;
    }
}
