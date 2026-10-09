package com.ncbagroup.main.utils;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.ncbagroup.main.exception.CountryNotFoundException;
import com.ncbagroup.main.models.entity.CountryInfoEntity;
import com.ncbagroup.main.models.request.xml.CountryInfo;
import com.ncbagroup.main.models.request.xml.all_countries_info.AllCountriesInfo;
import com.ncbagroup.main.models.request.xml.individual_country_info.IndividualCountryInfo;
import com.ncbagroup.main.repos.CountryInfoRepo;
import com.ncbagroup.main.service.impl.DatabaseWrites;
import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class Formatter {
    private static String FETCH_ISO_CODE = "fetchISOCode";
    private static String FETCH_FULL_COUNTRY_INFO = "fetchFullCountryInfo";
    private static String FETCH_ALL_COUNTRIES_INFO = "fetchAllCountries";
    private final CountryInfoRepo countryInfoRepo;
    private final XmlMapper xmlMapper = new XmlMapper();
    private final Configuration freemarkerConfig;
    private final DatabaseWrites databaseWrites;
    public Formatter(Configuration freemarkerConfig, DatabaseWrites databaseWrites,
                     CountryInfoRepo countryInfoRepo) {
        this.freemarkerConfig = freemarkerConfig;
        this.databaseWrites = databaseWrites;
        this.countryInfoRepo = countryInfoRepo;
    }

    public String mapOutgoingRequest(String placeholder, String action) {

        try {
            freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/templates");

            String fileName = switch (action) {
                case "fetchISOCode" -> "country_iso_request.ftl";
                case "fetchFullCountryInfo" -> "individual_country_info.ftl";
                case "fetchAllCountries" -> "all_countries_info.ftl";
                default -> "";
            };


            log.info("Invoking FreeMarker template: {} for action: {}", fileName, action);

            Template template = freemarkerConfig.getTemplate(fileName);
            StringWriter writer = new StringWriter();

            Map<String, Object> dataModel = new HashMap<>();
            dataModel.put("countryISOCode", placeholder != null ? placeholder : "");
            dataModel.put("countryName", placeholder != null ? placeholder : "");

            template.process(dataModel, writer);

            return writer.toString();

        } catch (Exception e) {
            log.error("Error occurred when mapping {} request ::: {}", action, e.getMessage(), e);
            return null;
        }
    }

    public String extractISOCode(String fetchISOCodeResponse) {
        log.info("Response received from ISO Code Invocation ::: \n" + fetchISOCodeResponse + "\n");

        if (fetchISOCodeResponse == null) {
            throw new RuntimeException("Response was null from upstream service");
        }

        String isoCode = StringUtils.substringBetween(fetchISOCodeResponse, "<m:CountryISOCodeResult>", "</m:CountryISOCodeResult>");

        if (isoCode == null) {
            throw new RuntimeException("Failed to extract ISO Code");
        }

        String trimmedIsoCode = isoCode.trim();

        if ("No country found by that name".equalsIgnoreCase(trimmedIsoCode)) {
            throw new CountryNotFoundException("No country found by that name");
        }

        return trimmedIsoCode;

    }


    public List<CountryInfoEntity> parseFullCountryInfoResponse(String xmlResponse, String action) {

        List<CountryInfoEntity> countryInfoEntities = new ArrayList<>();
        List<CountryInfo> countryInfoList = new ArrayList<>();

        log.info("Successfully fetched Full Country Info Response ::: \n{}\n", xmlResponse);

        try {

            if (action.equals(FETCH_FULL_COUNTRY_INFO)) {
                IndividualCountryInfo envelope = xmlMapper.readValue(xmlResponse, IndividualCountryInfo.class);


                if (envelope.getBody() == null || envelope.getBody().getResponse() == null) {
                    throw new RuntimeException("Error while processing downstream request");
                }


                CountryInfo countryInfo = envelope.getBody().getResponse().getResult();

                if (countryInfo == null) {
                    throw new CountryNotFoundException("No country information found");
                }

                countryInfoList.add(countryInfo);


            }


            if (action.equals(FETCH_ALL_COUNTRIES_INFO)) {
                AllCountriesInfo envelope = xmlMapper.readValue(xmlResponse, AllCountriesInfo.class);


                if (envelope.getBody() == null || envelope.getBody().getResponse() == null) {
                    throw new RuntimeException("Error while processing downstream request");
                }

                countryInfoList = envelope.getBody().getResponse().getCountries();

                if (countryInfoList == null || countryInfoList.isEmpty()) {
                    throw new CountryNotFoundException("No country information found");
                }
            }

            int count = 0;
            for (CountryInfo countryInfoXML : countryInfoList) {

                try {

                    CountryInfoEntity countryInfo = databaseWrites.saveCountryToDB(countryInfoXML);

                    countryInfoEntities.add(countryInfo);

                    count++;
                } catch (Exception e) {
                    log.error("Error while processing request ::: {}", e.getMessage());
                }
            }
            log.info("{} entities saved successfully", count);
            return countryInfoEntities;


        } catch (Exception e) {

            throw new RuntimeException("Failed to map Full Country Info Response: " + e.getMessage(), e);
        }
    }

}
