package com.ncbagroup.main.models.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class FullCountryInfoDto {

    // 1. Nested <tCountryInfo> list (All Countries XML)
    @JsonFormat(with = JsonFormat.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "tCountryInfo", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private List<CountryInfoResultDto> countryList;

    // 2. Direct fields (Single Country XML where FullCountryInfoResult IS the country)
    @JacksonXmlProperty(localName = "sISOCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private String isoCode;

    @JacksonXmlProperty(localName = "sName", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private String name;

    @JacksonXmlProperty(localName = "sCapitalCity", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private String capitalCity;

    @JacksonXmlProperty(localName = "sPhoneCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private String phoneCode;

    @JacksonXmlProperty(localName = "sContinentCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private String continentCode;

    @JacksonXmlProperty(localName = "sCurrencyISOCode", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private String currencyIsoCode;

    @JacksonXmlProperty(localName = "sCountryFlag", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private String countryFlag;

    @JacksonXmlElementWrapper(localName = "Languages", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JacksonXmlProperty(localName = "tLanguage", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    private List<LanguageDto> languages;

    /**
     * Normalizes both XML structures into a single List<CountryInfoResultDto>
     */
    public List<CountryInfoResultDto> toNormalizedList() {
        if (countryList != null && !countryList.isEmpty()) {
            return countryList;
        }

        if (isoCode != null) {
            CountryInfoResultDto directDto = new CountryInfoResultDto();
            directDto.setIsoCode(this.isoCode);
            directDto.setName(this.name);
            directDto.setCapitalCity(this.capitalCity);
            directDto.setPhoneCode(this.phoneCode);
            directDto.setContinentCode(this.continentCode);
            directDto.setCurrencyIsoCode(this.currencyIsoCode);
            directDto.setCountryFlag(this.countryFlag);
            directDto.setLanguages(this.languages);
            return List.of(directDto);
        }

        return List.of();
    }
}