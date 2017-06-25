package pl.mateuszzweigert.site;

import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pl.mateuszzweigert.site.model.LocaleRoutes;
import pl.mateuszzweigert.site.support.web.Routes;

import javax.servlet.http.HttpServletRequest;
import java.net.URL;
import java.security.Principal;
import java.util.Locale;

@Controller
public class HomeController {

	@RequestMapping(value = {Routes.HOME_1, Routes.HOME_2, Routes.HOME_3}, method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request) {
		model.addAttribute("routes", new LocaleRoutes(locale, request.getRequestURI()));
		return "home";
	}
}
