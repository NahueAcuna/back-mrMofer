package com.example.mrMoferBackend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class ReservationDetails {
    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    private Toilet toilet;
    @ManyToOne
    private Reservation reservation;
    private int quantity;
}
