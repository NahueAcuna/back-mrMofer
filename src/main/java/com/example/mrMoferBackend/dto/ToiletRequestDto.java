package com.example.mrMoferBackend.dto;

import lombok.Data;

@Data
public class ToiletRequestDto {
    private String name;
    private String imgURL;
    private String details;
    private double price;
    private boolean active;
    private int stock;
}
