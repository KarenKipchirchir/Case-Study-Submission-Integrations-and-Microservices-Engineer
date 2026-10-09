package com.ncbagroup.main.service;

import com.ncbagroup.main.models.entity.CountryInfoEntity;
import com.ncbagroup.main.models.request.CountryRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;

public interface CountryInterface {
    ResponseEntity<?> fetchFullCountryInfo(@Valid CountryRequest countryRequest);

    ResponseEntity<?> fetchAllCountries();

    ResponseEntity<?> getCountryById(String id);

    ResponseEntity<?> updateCountry(@Valid CountryInfoEntity countryInfo);

    ResponseEntity<?> deleteCountry(String id);
}
