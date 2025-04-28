package com.example.pidev.entity.activities;

import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import java.util.List;

public class LanguageToolResponse {
    private Software software;
    private Language language;
    private List<DataFormatReaders.Match> matches;

    public List<DataFormatReaders.Match> getMatches() {
        return matches;
    }

    public void setMatches(List<DataFormatReaders.Match> matches) {
        this.matches = matches;
    }

    public Software getSoftware() {
        return software;
    }

    public void setSoftware(Software software) {
        this.software = software;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public static class Software {
        private String name;
        private String version;
        // Getters et setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }
    }

    public static class Language {
        private String name;
        private String code;
        // Getters et setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}