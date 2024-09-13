package com.csc340.api_prototype;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Country {
    public String name;
    public String region;
    public String currency;

    public Country(String name, String region, String currency) {
        this.name = name;
        this.region = region;
        this.currency = currency;
    }

    public static class Name {
        private String common;

        @JsonProperty
        public String getCommon() {
            return common;
        }

        public void setCommon(String common) {
            this.common = common;
        }
    }

    public static class Currency {
        private String name;

        @JsonProperty
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
    public String getName() { return name; }
    public void setName() { this.name = name; }
    public String getRegion() { return region; }
    public void setRegion() { this.region = region; }
    public String getCurrency() { return currency; }
    public void setCurrency() { this.currency = currency; }
}
