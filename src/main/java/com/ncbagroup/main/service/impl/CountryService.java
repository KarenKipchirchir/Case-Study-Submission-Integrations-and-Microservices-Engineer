package com.ncbagroup.main.service.impl;

import com.google.gson.Gson;
import com.ncbagroup.main.client.HttpClient;
import com.ncbagroup.main.config.AppConfig;
import com.ncbagroup.main.models.entity.CountryInfoEntity;
import com.ncbagroup.main.models.entity.LanguageEntity;
import com.ncbagroup.main.models.request.CountryRequest;
import com.ncbagroup.main.models.response.ErrorResponse;
import com.ncbagroup.main.repos.CountryInfoRepo;
import com.ncbagroup.main.service.CountryInterface;
import com.ncbagroup.main.utils.Formatter;
import com.ncbagroup.main.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CountryService implements CountryInterface {
    private static String FETCH_ISO_CODE = "fetchISOCode";
    private static String FETCH_FULL_COUNTRY_INFO = "fetchFullCountryInfo";
    private static String FETCH_ALL_COUNTRIES_INFO = "fetchAllCountries";
    private final CountryInfoRepo countryInfoRepo;
    private final Utils utils;
    private final Formatter formatter;
    private final HttpClient httpClient;

    public CountryService(Utils utils, Formatter formatter, HttpClient httpClient,
                          CountryInfoRepo countryInfoRepo) {
        this.utils = utils;
        this.formatter = formatter;
        this.httpClient = httpClient;
        this.countryInfoRepo = countryInfoRepo;

    }

    @Override
    public ResponseEntity<?> fetchFullCountryInfo(CountryRequest countryRequest) {

        String countryName = utils.toSentenceCase(countryRequest.getName());


        // fect iso code
        String fetchISOCodeRequest = formatter.mapOutgoingRequest(countryName, FETCH_ISO_CODE);

        if (fetchISOCodeRequest == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Invoking Fetch ISO Code Request Leg");
        String fetchISOCodeResponse = httpClient.processRequest(fetchISOCodeRequest);

        String isoCode = formatter.extractISOCode(fetchISOCodeResponse);

        // check if info exists in db
        Optional<CountryInfoEntity> countryInfoOptional = countryInfoRepo.findByIsoCode(isoCode);


        if (countryInfoOptional.isPresent()) {

            return new ResponseEntity<>(countryInfoOptional.get(), HttpStatus.OK);
        }


        // info doesn't exist, invoke api
        String fullCountryInfoRequest = formatter.mapOutgoingRequest(isoCode, FETCH_FULL_COUNTRY_INFO);
        if (fullCountryInfoRequest == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("Invoking Full Country Info Request Leg");
        String fullCountryInfoResponse = httpClient.processRequest(fullCountryInfoRequest);

        List<CountryInfoEntity> countryInfoEntity = formatter.parseFullCountryInfoResponse(fullCountryInfoResponse, FETCH_FULL_COUNTRY_INFO);

        return new ResponseEntity<>(countryInfoEntity, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> fetchAllCountries() {

        String request = formatter.mapOutgoingRequest(null, FETCH_ALL_COUNTRIES_INFO);

        log.info("Invoking Fetch All Countries Request Leg");
        String fetchAllCountriesResponse = httpClient.processRequest(request);

        List<CountryInfoEntity> countryInfoEntity = formatter.parseFullCountryInfoResponse(fetchAllCountriesResponse, FETCH_ALL_COUNTRIES_INFO);

        return new ResponseEntity<>(countryInfoEntity, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCountryById(String id) {

        Optional<CountryInfoEntity> countryInfo;

        try {
            countryInfo = countryInfoRepo.findByIsoCode(id);
        } catch (Exception e) {
            return new ResponseEntity<>(mapErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (countryInfo.isPresent()) {
            return new ResponseEntity<>(countryInfo.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(mapErrorResponse(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ResponseEntity<?> updateCountry(CountryInfoEntity countryInfo) {
        Optional<CountryInfoEntity> countryInfoEntity;

        try {
            countryInfoEntity = countryInfoRepo.findByIsoCode(countryInfo.getIsoCode());
        } catch (Exception e) {

            return new ResponseEntity<>(mapErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (countryInfoEntity.isEmpty()) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.NOT_FOUND.value())
                    .error("Country info not found")
                    .message("ISO Code passed does not exist")
                    .errors(null)
                    .build();
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        CountryInfoEntity entity = new CountryInfoEntity();
        entity.setIsoCode(countryInfo.getIsoCode());
        entity.setName(countryInfoEntity.get().getName());
        entity.setCapitalCity(countryInfoEntity.get().getCapitalCity());
        entity.setPhoneCode(countryInfoEntity.get().getPhoneCode());
        entity.setContinentCode(countryInfoEntity.get().getContinentCode());
        entity.setCurrencyIsoCode(countryInfoEntity.get().getCurrencyIsoCode());
        entity.setCountryFlag(countryInfoEntity.get().getCountryFlag());
        entity.setUpdatedAt(LocalDateTime.now());

        if (!countryInfo.getLanguages().isEmpty()) {
            for (LanguageEntity language : countryInfo.getLanguages()) {
                LanguageEntity languageEntity = new LanguageEntity();
                languageEntity.setIsoCode(language.getIsoCode());
                languageEntity.setName(language.getName());
                entity.addLanguage(languageEntity);
            }
        }

        countryInfoRepo.save(entity);

        ErrorResponse response = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.OK.value())
                .error("Country info successfully updated")
                .message(null)
                .errors(null)
                .build();

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteCountry(String id) {
        Optional<CountryInfoEntity> countryInfoEntity;

        try {
            countryInfoEntity = countryInfoRepo.findByIsoCode(id);
        } catch (Exception e) {
            return new ResponseEntity<>(mapErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value()), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (countryInfoEntity.isPresent()) {
            countryInfoRepo.delete(countryInfoEntity.get());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {

            return new ResponseEntity<>(mapErrorResponse(HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
        }
    }

    private ErrorResponse mapErrorResponse(int value) {

        if (value == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .error("Error occurred when processing this request")
                    .message("")
                    .errors(null)
                    .build();
        }

        if (value == HttpStatus.NOT_FOUND.value()) {
            return ErrorResponse.builder()
                    .timestamp(LocalDateTime.now())
                    .status(HttpStatus.NOT_FOUND.value())
                    .error("Country info not found")
                    .message("Identifier passed does not exist")
                    .errors(null)
                    .build();
        }
        return null;
    }

}
