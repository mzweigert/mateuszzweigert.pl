package pl.mateuszzweigert.site.common;

import com.google.common.base.Throwables;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import pl.mateuszzweigert.site.model.LocaleRoutes;
import pl.mateuszzweigert.site.common.Views;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler {

    /**
     * Handle exceptions thrown by handlers.
     */
    @org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
    public String exception(Exception exception, Locale locale, Model model, HttpServletRequest request) {
        model.addAttribute("routes", new LocaleRoutes(locale, request.getRequestURI()));
        model.addAttribute("errorMessage", Throwables.getRootCause(exception));
        return Views.ERROR_VIEW;
    }
}