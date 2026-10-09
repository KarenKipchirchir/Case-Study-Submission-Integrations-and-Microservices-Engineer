package com.ncbagroup.main.service.impl;

import com.ncbagroup.main.models.entity.CountryInfoEntity;
import com.ncbagroup.main.models.entity.LanguageEntity;
import com.ncbagroup.main.models.request.xml.CountryInfo;
import com.ncbagroup.main.models.request.xml.Language;
import com.ncbagroup.main.repos.CountryInfoRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class DatabaseWrites {

    private final CountryInfoRepo countryInfoRepo;

    public DatabaseWrites(CountryInfoRepo countryInfoRepo) {
        this.countryInfoRepo = countryInfoRepo;
    }

    public CountryInfoEntity saveCountryToDB(CountryInfo result) {
        CountryInfoEntity countryInfoEntity = new CountryInfoEntity();
        countryInfoEntity.setIsoCode(result.getIsoCode());
        countryInfoEntity.setName(result.getName());
        countryInfoEntity.setCapitalCity(result.getCapitalCity());
        countryInfoEntity.setPhoneCode(result.getPhoneCode());
        countryInfoEntity.setContinentCode(result.getContinentCode());
        countryInfoEntity.setCurrencyIsoCode(result.getCurrencyIsoCode());
        countryInfoEntity.setCountryFlag(result.getCountryFlag());
        countryInfoEntity.setCreatedAt(LocalDateTime.now());

        if (result.getLanguages() != null) {
            for (Language languageDto : result.getLanguages()) {
                LanguageEntity languageEntity = new LanguageEntity();
                languageEntity.setIsoCode(languageDto.getIsoCode());
                languageEntity.setName(languageDto.getName());
                countryInfoEntity.addLanguage(languageEntity);
            }
        }


        countryInfoRepo.save(countryInfoEntity);
        return countryInfoEntity;
    }
}
