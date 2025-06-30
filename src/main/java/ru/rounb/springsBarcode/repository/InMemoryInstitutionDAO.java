package ru.rounb.springsBarcode.repository;

import org.springframework.stereotype.Repository;
import ru.rounb.springsBarcode.model.Institution;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Repository
public class InMemoryInstitutionDAO {
    private final List<Institution> INSTITUTIONS = new ArrayList<>();

    public List<Institution> findAllInstitution() {
        return INSTITUTIONS;
    }

    public Institution saveInstitution(Institution institution) {
        INSTITUTIONS.add(institution);
        return institution;
    }

    public Institution findByKey(String key) {
        return INSTITUTIONS.stream()
                .filter(element -> element.getId().equals(key))
                .findFirst()
                .orElse(null);
    }

    public Institution updateInstitution(Institution institution) {
        int institutionIndex = IntStream.range(0, INSTITUTIONS.size())
                .filter(index -> INSTITUTIONS.get(index).getId().equals(institution.getKey()))
                .findFirst()
                .orElse(-1);
        if (institutionIndex > -1) {
            INSTITUTIONS.set(institutionIndex, institution);
            return institution;
        }
        return null;
    }

    public void deleteInstitution(String key) {
         Institution institution = findByKey(key);
         if (institution != null) {
            INSTITUTIONS.remove(institution);
         }
    }

    public void deleteAllInstitution() {
        INSTITUTIONS.clear();
    }


}
