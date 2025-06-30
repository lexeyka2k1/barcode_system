package ru.rounb.springsBarcode.dto;

import lombok.Getter;

@Getter
public class DocumentInfoResponse {
    private String inventoryNumber;
    private String title;

    public DocumentInfoResponse(String inventoryNumber, String title) {
        this.inventoryNumber = inventoryNumber;
        this.title = title;
    }

    public void setInventoryNumber(String inventoryNumber) {
        this.inventoryNumber = inventoryNumber;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
