package ru.rounb.springsBarcode.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.rounb.springsBarcode.model.Institution;

public interface InstitutionRepository extends JpaRepository<Institution, Long> {
    Institution findInstitutionByKey(String key);
    Institution findInstitutionByLogin(String login);
    void deleteByKey(String key);
}
