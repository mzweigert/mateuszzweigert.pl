package pl.mateuszzweigert.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.mateuszzweigert.site.common.Routes;
import pl.mateuszzweigert.site.common.Views;
import pl.mateuszzweigert.site.info.DynamicInfoRepository;

import java.util.Locale;

@Controller
public class HomeController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private DynamicInfoRepository dynamicInfoRepository;

    @GetMapping(value = {Routes.HOME_1, Routes.HOME_2, Routes.HOME_3})
    public String home(Model model) {
        Locale locale = LocaleContextHolder.getLocale();
        String aboutMe = dynamicInfoRepository.findValueByKeyAndLanguage("about_me", locale.getLanguage());
        if(aboutMe == null) {
            aboutMe = messageSource.getMessage("view.index.desc.about_me", null, locale);
        }
        model.addAttribute("about_me", aboutMe);
        return Views.HOME_VIEW;
    }
}
