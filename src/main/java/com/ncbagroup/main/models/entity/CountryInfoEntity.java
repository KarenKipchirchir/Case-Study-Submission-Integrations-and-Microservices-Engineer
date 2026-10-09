package com.ncbagroup.main.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "country_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CountryInfoEntity {

    @Id
    private String isoCode;

    private String name;
    private String capitalCity;
    private String phoneCode;
    private String continentCode;
    private String currencyIsoCode;
    private String countryFlag;

    @JsonIgnore
    private LocalDateTime createdAt;
    @JsonIgnore
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "countryInfoEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<LanguageEntity> languages = new ArrayList<>();

    public void addLanguage(LanguageEntity languageEntity) {
        languages.add(languageEntity);
        languageEntity.setCountryInfoEntity(this);
    }
}