package com.example.mrMoferBackend.dto;

import com.example.mrMoferBackend.entity.ReservationDetails;
import com.example.mrMoferBackend.entity.User;
import com.example.mrMoferBackend.enums.ReservationStatus;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReservationRequestDto {

    private String clientName;
    private String phoneNumber;
    private List<ReservationDetailsDto> details =  new ArrayList<>();
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
}
