package com.ncbagroup.main.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LanguageDto {

    @JacksonXmlProperty(localName = "sISOCode")
    private String isoCode;

    @JacksonXmlProperty(localName = "sName")
    private String name;
}