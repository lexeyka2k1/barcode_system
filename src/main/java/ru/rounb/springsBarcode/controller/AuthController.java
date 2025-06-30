package ru.rounb.springsBarcode.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.rounb.springsBarcode.dto.AuthRequest;
import ru.rounb.springsBarcode.dto.AuthResponse;
import ru.rounb.springsBarcode.model.Institution;
import ru.rounb.springsBarcode.service.InstitutionService;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
    private final InstitutionService institutionService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Institution user = institutionService.authenticate(
                request.getLogin(),
                request.getPassword()
        );

        if (user != null) {
            return ResponseEntity.ok(new AuthResponse(
                    user.getKey(),
                    user.getName(),
                    user.getRole().name()
            ));
        }
        return ResponseEntity.status(401).build();
    }
}


