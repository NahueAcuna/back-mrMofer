package com.example.mrMoferBackend.entity;

import com.example.mrMoferBackend.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Reservation {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "reservation")
    private List<ReservationDetails> reservationDetails;
    private LocalDate createdDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private double price;
    private ReservationStatus status;

}
