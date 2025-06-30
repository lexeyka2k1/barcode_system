package ru.rounb.springsBarcode.service;

import org.springframework.stereotype.Service;
import ru.rounb.springsBarcode.model.Institution;

import java.util.List;

public interface InstitutionService {

    //Найти все учреждения
    List<Institution> findAllInstitution();

    //Сохранение нового учреждения
    Institution saveInstitution(Institution institution);

    //Найти учреждение по key
    Institution findByKey(String key);

    //Обновить данные учреждения
    Institution updateInstitution(Institution institution);

    //Удалить учреждение из списка
    void deleteInstitution(String key);

    void deleteAllInstitution();

    Institution authenticate(String login, String password);

    Institution updateDatabases(String key, List<Integer> databases, String action);

}
