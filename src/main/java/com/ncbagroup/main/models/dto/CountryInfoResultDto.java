package com.ncbagroup.main.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryInfoResultDto {

    @JacksonXmlProperty(localName = "sISOCode")
    private String isoCode;

    @JacksonXmlProperty(localName = "sName")
    private String name;

    @JacksonXmlProperty(localName = "sCapitalCity")
    private String capitalCity;

    @JacksonXmlProperty(localName = "sPhoneCode")
    private String phoneCode;

    @JacksonXmlProperty(localName = "sContinentCode")
    private String continentCode;

    @JacksonXmlProperty(localName = "sCurrencyISOCode")
    private String currencyIsoCode;

    @JacksonXmlProperty(localName = "sCountryFlag")
    private String countryFlag;

    @JacksonXmlElementWrapper(localName = "Languages")
    @JacksonXmlProperty(localName = "tLanguage")
    private List<LanguageDto> languages;
}