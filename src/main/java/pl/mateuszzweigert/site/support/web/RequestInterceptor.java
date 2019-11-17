package pl.mateuszzweigert.site.support.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pl.mateuszzweigert.site.model.LocaleRoutes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.Locale;

public class RequestInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private MessageSource messageSource;

    private static final String COPYRIGHT_PROPERTY = "view.index.copyright";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView == null) {
            return;
        }
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(COPYRIGHT_PROPERTY, null, locale);

        modelAndView.addObject("copyright", String.format(message, LocalDateTime.now().getYear()));
        LocaleRoutes routes = new LocaleRoutes(locale, httpServletRequest.getRequestURI());
        modelAndView.addObject("routes", routes);
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
