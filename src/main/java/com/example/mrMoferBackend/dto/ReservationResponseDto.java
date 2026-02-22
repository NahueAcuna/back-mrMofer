package com.example.mrMoferBackend.dto;

import com.example.mrMoferBackend.entity.ReservationDetails;
import com.example.mrMoferBackend.entity.User;
import com.example.mrMoferBackend.enums.ReservationStatus;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReservationResponseDto {
    private Long id;
    private User user;
    private List<ReservationDetailsDto> reservationDetails =  new ArrayList<>();
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private ReservationStatus status;
}
