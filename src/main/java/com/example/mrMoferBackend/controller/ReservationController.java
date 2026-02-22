package com.example.mrMoferBackend.controller;

import com.example.mrMoferBackend.dto.ReservationRequestDto;
import com.example.mrMoferBackend.service.ReservationService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> postReservation(@RequestBody ReservationRequestDto reservationRequestDto) {

        try {
            ReservationRequestDto savedReservation = reservationService.createReservation(reservationRequestDto);
            return  ResponseEntity.ok().body(savedReservation);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity<?> getReservation(){

        List<ReservationRequestDto> allReservations =  reservationService.getReservations();
        return ResponseEntity.ok(allReservations);
    }

}
