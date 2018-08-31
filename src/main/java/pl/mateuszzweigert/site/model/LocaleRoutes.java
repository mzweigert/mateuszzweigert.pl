package pl.mateuszzweigert.site.model;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Locale;

public class LocaleRoutes {

    private static final String LOCALE_PARAM = "?locale=";
    private static final List<String> supportedLanguages = Lists.newArrayList(
            "pl", "en"
    );

    private String currentPage;
    private String lang;

    public LocaleRoutes(Locale locale, String currentPage) {
        this.lang = resolveLang(locale);
        String langUrl = resolveUrl(this.lang);
        this.currentPage = currentPage + langUrl;
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

    public String getCurrentPage() {
        return currentPage;
    }

}
