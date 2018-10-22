package pl.mateuszzweigert.site.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public enum PrivacyPolicyApp {

    TAP_THE_DOTS("Tap The Dots", Collections.singletonList("Cookies and Usage Data"), "Poland", "mateuszzweigert@gmail.com"),
    FRACTAL_REALITY("Fractal Reality", Collections.singletonList("Cookies and Usage Data"), "Poland", "mateuszzweigert@gmail.com");

    private static final Logger logger = LoggerFactory.getLogger(PrivacyPolicyApp.class);
    private String appName;
    private List<String> collectedData;
    private String country;
    private String contactEmail;

    PrivacyPolicyApp(String appName, List<String> collectedData,
                     String country, String contactEmail) {
        this.appName = appName;
        this.collectedData = collectedData;
        this.country = country;
        this.contactEmail = contactEmail;
    }

    public static PrivacyPolicyApp of(String appName) {
        switch (appName.toLowerCase()) {
            case "tap-the-dots":
                return TAP_THE_DOTS;
            case "fractal-reality":
                return FRACTAL_REALITY;
            default:
                logger.error("Cannot find app with name: " + appName);
                return null;

        }
    }

    public String getAppName() {
        return appName;
    }

    public List<String> getCollectedData() {
        return collectedData;
    }

    public String getCountry() {
        return country;
    }

    public String getContactEmail() {
        return contactEmail;
    }
}
