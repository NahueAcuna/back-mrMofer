package com.example.mrMoferBackend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Toilet {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String imgURL;
    private String details;
    private double price;
    private boolean active;
    private int stock;
    @ElementCollection
    private List<String> qualities =  new ArrayList<>();

}
