package pl.mateuszzweigert.site.model;

import pl.mateuszzweigert.site.common.Routes;

import java.util.Locale;

public class LocaleRoutes {

    private String currentPage;
    private String lang;
    private String home;
    private String skills;
    private String projects;
    private String contact;

    private static final String LOCALE_EN_PARAM = "?locale=en";
    private static final String LOCALE_PL_PARAM = "?locale=pl";

    public LocaleRoutes(Locale locale, String currentPage) {
        this.lang = locale.getLanguage();
        String langUrl = this.lang.equals("pl") ? LOCALE_EN_PARAM : LOCALE_PL_PARAM;
        this.currentPage = currentPage + langUrl;
        this.home = Routes.HOME_1 + langUrl;
        this.skills = Routes.SKILLS + langUrl;
        this.projects = Routes.PROJECTS + langUrl;
        this.contact = Routes.CONTACT + langUrl;
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
