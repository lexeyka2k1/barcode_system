package ru.rounb.springsBarcode.service;

import org.springframework.stereotype.Service;
import ru.rounb.springsBarcode.model.Institution;
import ru.rounb.springsBarcode.model.Role;

@Service
public class AuthorizationService {
    public boolean isAdmin(Institution user) {
        return user != null && user.getRole() == Role.ADMIN;
    }

    public boolean hasAccess(Institution user, String requiredRole) {
        if (user == null) return false;
        if (Role.ADMIN.name().equals(requiredRole)) {
            return user.getRole() == Role.ADMIN;
        }
        return true;
    }
}