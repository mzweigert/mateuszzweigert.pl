package pl.mateuszzweigert.site;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.common.Views;

@Controller
public class HomeController {

    @GetMapping(value = {Routes.HOME_1, Routes.HOME_2, Routes.HOME_3})
    public String home() {
        return Views.HOME_VIEW;
    }
}
