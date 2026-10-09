package com.ncbagroup.main.models.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoapBodyDto {

    @JacksonXmlProperty(localName = "FullCountryInfoResponse", namespace = "http://www.oorsprong.org/websamples.countryinfo")
    @JsonAlias({"FullCountryInfoAllCountriesResponse", "FullCountryInfoResponse"})
    private FullCountryInfoResultWrapperDto responseWrapper;
}