package com.ncbagroup.main.controller;

import com.ncbagroup.main.config.AppConfig;
import com.ncbagroup.main.models.entity.CountryInfoEntity;
import com.ncbagroup.main.models.request.CountryRequest;
import com.ncbagroup.main.service.CountryInterface;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/countries")
@Slf4j
public class CountryController {

    private final CountryInterface countryInterface;
    private final AppConfig appConfig;

    public CountryController(CountryInterface countryInterface, AppConfig appConfig) {
        this.countryInterface = countryInterface;
        this.appConfig = appConfig;
    }


    @PostMapping("/getFullCountryInfo")
    public ResponseEntity<?> fetchFullCountryInfo(@Valid @RequestBody CountryRequest countryRequest) {
        log.info("Received request to fetch full country info ::: {}", appConfig.gson().toJson(countryRequest));
        return countryInterface.fetchFullCountryInfo(countryRequest);

    }

    @GetMapping("/getAllCountriesInfo")
    public ResponseEntity<?> fetchAllCountries() {
        log.info("Received request to fetch all countries");
        return countryInterface.fetchAllCountries();
    }


    @GetMapping("/getCountryInfo/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable String id) {
        return countryInterface.getCountryById(id);
    }

    @PutMapping("/updateCountryInfo")
    public ResponseEntity<?> updateCountry(@Valid @RequestBody CountryInfoEntity countryInfo) {
        return countryInterface.updateCountry(countryInfo);
    }

    @DeleteMapping("deleteCountry/{id}")
    public ResponseEntity<?> deleteCountry(@PathVariable String id) {
        return countryInterface.deleteCountry(id);

    }
}