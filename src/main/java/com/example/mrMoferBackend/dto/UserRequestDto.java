package com.example.mrMoferBackend.dto;

import com.example.mrMoferBackend.entity.Reservation;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDto {
    private String name;
    private String email;
    private String phoneNumber;
}
