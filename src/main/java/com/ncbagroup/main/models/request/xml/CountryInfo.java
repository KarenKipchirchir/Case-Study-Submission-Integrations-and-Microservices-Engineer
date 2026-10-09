package com.ncbagroup.main.models.request.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInfo {

    @JacksonXmlProperty(localName = "sISOCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("isoCode")
    private String isoCode;

    @JacksonXmlProperty(localName = "sName", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("name")
    private String name;

    @JacksonXmlProperty(localName = "sCapitalCity", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("capitalCity")
    private String capitalCity;

    @JacksonXmlProperty(localName = "sPhoneCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("phoneCode")
    private String phoneCode;

    @JacksonXmlProperty(localName = "sContinentCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("continentCode")
    private String continentCode;

    @JacksonXmlProperty(localName = "sCurrencyISOCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("currencyIsoCode")
    private String currencyIsoCode;

    @JacksonXmlProperty(localName = "sCountryFlag", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("countryFlag")
    private String countryFlag;

    @JacksonXmlElementWrapper(localName = "Languages", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JacksonXmlProperty(localName = "tLanguage", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("languages")
    private List<Language> languages;

    // Getters and Setters
    public String getIsoCode() {
        return isoCode;
    }

    public String getName() {
        return name;
    }

    public String getCapitalCity() {
        return capitalCity;
    }

    public String getPhoneCode() {
        return phoneCode;
    }

    public String getContinentCode() {
        return continentCode;
    }

    public String getCurrencyIsoCode() {
        return currencyIsoCode;
    }

    public String getCountryFlag() {
        return countryFlag;
    }

    public List<Language> getLanguages() {
        return languages;
    }
}

