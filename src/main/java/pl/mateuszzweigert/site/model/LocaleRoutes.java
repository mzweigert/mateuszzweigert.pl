package pl.mateuszzweigert.site.model;

import com.google.common.collect.Lists;
import pl.mateuszzweigert.site.common.Routes;

import java.util.List;
import java.util.Locale;

public class LocaleRoutes {

    private static final String LOCALE_PARAM = "?locale=";
    private static final List<String> supportedLanguages = Lists.newArrayList(
            "pl", "en"
    );

    private String currentPage;
    private String lang;
    private String home;
    private String skills;
    private String projects;
    private String contact;

    public LocaleRoutes(Locale locale, String currentPage) {
        this.lang = resolveLang(locale);
        String langUrl = resolveUrl(this.lang);
        this.currentPage = currentPage + langUrl;
        this.home = Routes.HOME_1 + langUrl;
        this.skills = Routes.SKILLS + langUrl;
        this.projects = Routes.PROJECTS + langUrl;
        this.contact = Routes.CONTACT + langUrl;
    }

    private String resolveUrl(String lang) {
        switch (lang) {
            case "pl" :
                return LOCALE_PARAM + "en";
            case "en":
            default:
                return LOCALE_PARAM + "pl";
        }
    }

    private String resolveLang(Locale locale) {
        return supportedLanguages.contains(locale.getLanguage()) ?
                locale.getLanguage() :
                Locale.getDefault().getLanguage();
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getProjects() {
        return projects;
    }

    public void setProjects(String projects) {
        this.projects = projects;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }
}
