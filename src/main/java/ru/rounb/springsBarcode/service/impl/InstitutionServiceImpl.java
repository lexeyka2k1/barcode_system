package ru.rounb.springsBarcode.service.impl;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import ru.rounb.springsBarcode.model.Institution;
import ru.rounb.springsBarcode.repository.InstitutionRepository;
import ru.rounb.springsBarcode.service.InstitutionService;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class InstitutionServiceImpl implements InstitutionService {

    private final InstitutionRepository repository;
    @Override
    public List<Institution> findAllInstitution() {
        return repository.findAll();
    }

    @Override
    public Institution saveInstitution(Institution institution) {
        return repository.save(institution);
    }

    @Override
    public Institution findByKey(String key) {
        return repository.findInstitutionByKey(key);
    }


    @Override
    public Institution updateInstitution(Institution institution) {
        return repository.save(institution);
    }

    @Override
    @Transactional
    public void deleteInstitution(String key) {
        repository.deleteByKey(key);
    }

    @Override
    public void deleteAllInstitution() {
        repository.deleteAll();;
    }

    @Override
    public Institution authenticate(String login, String password) {
        Institution user = repository.findInstitutionByLogin(login);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    @Override
    public Institution updateDatabases(String key, List<Integer> databases, String action) {
        Institution institution = repository.findInstitutionByKey(key);
        if (institution == null) {
            throw new RuntimeException("Institution not found");
        }

        List<Integer> currentDbs = institution.getAvailableDatabases();

        if ("add".equalsIgnoreCase(action)) {
            for (Integer db : databases) {
                if (!currentDbs.contains(db)) {
                    currentDbs.add(db);
                }
            }
        } else if ("remove".equalsIgnoreCase(action)) {
            currentDbs.removeAll(databases);
        } else {
            throw new RuntimeException("Invalid action: " + action);
        }

        institution.setAvailableDatabases(currentDbs);
        return updateInstitution(institution);
    }
}
