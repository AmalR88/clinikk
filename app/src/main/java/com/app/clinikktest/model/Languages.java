package com.app.clinikktest.model;

public class Languages {

    private String language;
    private String languageCode;

    public Languages() {
    }

    public Languages(String language, String languageCode) {
        this.language = language;
        this.languageCode = languageCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguageCode() {
        return languageCode;
    }

    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }


}
