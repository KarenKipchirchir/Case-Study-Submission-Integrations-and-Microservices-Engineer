package com.ncbagroup.main.repos;

import com.ncbagroup.main.models.entity.CountryInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryInfoRepo extends JpaRepository<CountryInfoEntity, String> {
    Optional<CountryInfoEntity> findByIsoCode(String isoCode);
}
