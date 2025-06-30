package ru.rounb.springsBarcode.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.rounb.springsBarcode.dto.DatabaseUpdateRequest;
import ru.rounb.springsBarcode.model.Institution;
import ru.rounb.springsBarcode.service.AuthorizationService;
import ru.rounb.springsBarcode.service.InstitutionService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/institutions")
@AllArgsConstructor
public class InstitutionController {

    private final InstitutionService service;
    private final AuthorizationService authService;

    @GetMapping
    public List<Institution> findAllInstitution(@RequestHeader("Authorization") String authKey) {
        Institution currentUser = service.findByKey(authKey);
        if (!authService.isAdmin(currentUser)) {
            throw new RuntimeException("Access denied");
        }
        return service.findAllInstitution();
    }

    @PostMapping("/save_institution")
    public Institution saveInstitution(@RequestBody Institution institution,
                                       @RequestHeader("Authorization") String authKey) {
        Institution currentUser = service.findByKey(authKey);
        if (!authService.isAdmin(currentUser)) {
            throw new RuntimeException("Access denied");
        }
        return service.saveInstitution(institution);
    }

    @GetMapping("/{key}")
    public Institution findByKey(@PathVariable String key,
                                 @RequestHeader("Authorization") String authKey) {
        Institution currentUser = service.findByKey(authKey);
        if (currentUser == null) {
            throw new RuntimeException("Unauthorized");
        }
        return service.findByKey(key);
    }

    @PutMapping("/update_institution")
    public Institution updateInstitution(@RequestBody Institution institution,
                                         @RequestHeader("Authorization") String authKey) {
        Institution currentUser = service.findByKey(authKey);
        if (!authService.isAdmin(currentUser)) {
            throw new RuntimeException("Access denied");
        }
        return service.updateInstitution(institution);
    }

    @DeleteMapping("/delete_institution/{key}")
    public void deleteInstitution(@PathVariable String key,
                                  @RequestHeader("Authorization") String authKey) {
        Institution currentUser = service.findByKey(authKey);
        if (!authService.isAdmin(currentUser)) {
            throw new RuntimeException("Access denied");
        }
        service.deleteInstitution(key);
    }

    @DeleteMapping("/delete_all_institution")
    public void deleteAllInstitution(@RequestHeader("Authorization") String authKey) {
        Institution currentUser = service.findByKey(authKey);
        if (!authService.isAdmin(currentUser)) {
            throw new RuntimeException("Access denied");
        }
        service.deleteAllInstitution();
    }

    @GetMapping("/current")
    public Institution getCurrentUser(@RequestHeader("Authorization") String authKey) {
        Institution user = service.findByKey(authKey);
        if (user == null) {
            throw new RuntimeException("Unauthorized");
        }
        return user;
    }

    @PostMapping("/update_databases/{key}")
    public Institution updateDatabases(@PathVariable String key,
                                       @RequestBody DatabaseUpdateRequest request,
                                       @RequestHeader("Authorization") String authKey) {
        Institution currentUser = service.findByKey(authKey);
        if (!authService.isAdmin(currentUser)) {
            throw new RuntimeException("Access denied");
        }
        return service.updateDatabases(key, request.getDatabases(), request.getAction());
    }


}
