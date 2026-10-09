package com.ncbagroup.main.models.request.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Language {

    @JacksonXmlProperty(localName = "sISOCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("isoCode")
    private String isoCode;

    @JacksonXmlProperty(localName = "sName", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonProperty("name")
    private String name;

    // Getters and Setters
    public String getIsoCode() {
        return isoCode;
    }

    public String getName() {
        return name;
    }
}