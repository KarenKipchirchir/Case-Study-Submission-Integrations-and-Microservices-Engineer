package com.ncbagroup.main.repos;

import com.ncbagroup.main.models.entity.LanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepo extends JpaRepository<LanguageEntity, Long> {
}