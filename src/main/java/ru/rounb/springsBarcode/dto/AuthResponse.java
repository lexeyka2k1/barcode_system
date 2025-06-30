package ru.rounb.springsBarcode.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String key;
    private String name;
    private String role;
}