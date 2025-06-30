package ru.rounb.springsBarcode.dto;

import lombok.Data;
import java.util.List;

@Data
public class DatabaseUpdateRequest {
    private List<Integer> databases;
    private String action; // "add" или "remove"
}