package ru.rounb.springsBarcode.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.rounb.springsBarcode.model.Institution;
import ru.rounb.springsBarcode.model.Role;
import ru.rounb.springsBarcode.repository.InstitutionRepository;

import java.util.List;

@Configuration
public class AdminInitializer {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initAdmin(InstitutionRepository repository) {
        return args -> {
            if (repository.findInstitutionByLogin("admin") == null) {
                Institution admin = new Institution();
                admin.setKey("000");
                admin.setName("Admin");
                admin.setLogin("admin");
                admin.setPassword(passwordEncoder.encode("!234Qwer"));  // Пароль будет захеширован
                admin.setRole(Role.ADMIN);
                admin.setAvailableDatabases(List.of(1, 2, 3));
                repository.save(admin);
            }
        };
    }
}