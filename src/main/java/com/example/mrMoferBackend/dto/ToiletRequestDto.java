package com.example.mrMoferBackend.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ToiletRequestDto {
    private String name;
    private String imgURL;
    private String details;
    private double price;
    private boolean active;
    private int stock;
    private List<String> qualities = new ArrayList<>();
}
