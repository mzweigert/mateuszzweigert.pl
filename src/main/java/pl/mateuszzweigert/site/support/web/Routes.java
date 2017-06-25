package pl.mateuszzweigert.site.support.web;

import java.util.Locale;

public class Routes {
    public static final String HOME_1 = "/home";
    public static final String HOME_2 =  "/" ;
    public static final String HOME_3 =  "/welcome" ;

    public static final String SKILLS = "/skills";
    public static final String PROJECTS = "/projects";
    public static final String CONTACT = "/contact";

    public static String getHRefByLocale(Locale locale, String requestURI) {
        return requestURI + "?locale=" + locale.getLanguage();
    }
}
