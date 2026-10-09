package com.ncbagroup.main.models.request.xml.all_countries_info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ncbagroup.main.models.request.xml.CountryInfo;

import java.util.List;

@JacksonXmlRootElement(localName = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AllCountriesInfo {

    @JacksonXmlProperty(localName = "Body", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private SoapBody body;

    public SoapBody getBody() {
        return body;
    }

    public void setBody(SoapBody body) {
        this.body = body;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class SoapBody {

        @JacksonXmlProperty(localName = "FullCountryInfoAllCountriesResponse", namespace = "http://www.oorsprong.org/websamples.countryinfo")
        private FullCountryInfoAllCountriesResponse response;

        public FullCountryInfoAllCountriesResponse getResponse() {
            return response;
        }

        public void setResponse(FullCountryInfoAllCountriesResponse response) {
            this.response = response;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FullCountryInfoAllCountriesResponse {

        @JacksonXmlElementWrapper(localName = "FullCountryInfoAllCountriesResult", namespace = "http://www.oorsprong.org/websamples.countryinfo")
        @JacksonXmlProperty(localName = "tCountryInfo", namespace = "http://www.oorsprong.org/websamples.countryinfo")
        private List<CountryInfo> countries;

        public List<CountryInfo> getCountries() {
            return countries;
        }

        public void setCountries(List<CountryInfo> countries) {
            this.countries = countries;
        }
    }


}