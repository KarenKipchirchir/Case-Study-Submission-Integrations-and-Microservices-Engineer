package com.ncbagroup.main.models.request.xml.individual_country_info;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.ncbagroup.main.models.request.xml.CountryInfo;

@JacksonXmlRootElement(localName = "Envelope", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndividualCountryInfo {

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

        @JacksonXmlProperty(localName = "FullCountryInfoResponse", namespace = "http://www.oorsprong.org/websamples.countryinfo")
        private FullCountryInfoResponse response;

        public FullCountryInfoResponse getResponse() {
            return response;
        }

        public void setResponse(FullCountryInfoResponse response) {
            this.response = response;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class FullCountryInfoResponse {

        @JacksonXmlProperty(localName = "FullCountryInfoResult", namespace = "http://www.oorsprong.org/websamples.countryinfo")
        private CountryInfo result;

        public CountryInfo getResult() {
            return result;
        }

        public void setResult(CountryInfo result) {
            this.result = result;
        }
    }

}