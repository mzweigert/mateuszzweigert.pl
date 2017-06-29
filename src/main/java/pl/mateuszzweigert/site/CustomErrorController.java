package pl.mateuszzweigert.site;

import com.google.common.base.Throwables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.mateuszzweigert.site.support.web.Routes;
import pl.mateuszzweigert.site.support.web.Views;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;

@Controller
class CustomErrorController {

    @Autowired
    private MessageSource messageSource;

    private static final String GENERAL_ERROR_PROPERTY = "view.error.general";
    private static final String UNKNOWN_ERROR_PROPERTY = "view.error.unknown";

    /**
     * Display an error page, as defined in web.xml <code>custom-error</code> element.
     */
    @RequestMapping(Routes.ERROR)
    public String generalError(HttpServletRequest request, Model model) {
        // retrieve some useful information from the request
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        // String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
        String exceptionMessage = getExceptionMessage(throwable, statusCode);

        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null || exceptionMessage == null) {
            model.addAttribute("errorMessage", getMessage(UNKNOWN_ERROR_PROPERTY));
        } else {
            String message = MessageFormat.format(getMessage(GENERAL_ERROR_PROPERTY),
                    statusCode, requestUri, exceptionMessage);
            model.addAttribute("errorMessage", message);
        }
        return Views.ERROR_VIEW;
    }

    private String getMessage(String property) {
        return messageSource.getMessage(property, null, LocaleContextHolder.getLocale());
    }

    private String getExceptionMessage(Throwable throwable, Integer statusCode) {
        if (throwable != null) {
            return Throwables.getRootCause(throwable).getMessage();
        }
        if (statusCode != null) {
            return HttpStatus.valueOf(statusCode).getReasonPhrase();
        }

        return null;
    }
}
