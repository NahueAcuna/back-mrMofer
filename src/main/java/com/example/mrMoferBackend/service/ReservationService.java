package com.example.mrMoferBackend.service;

import com.example.mrMoferBackend.dto.ReservationRequestDto;
import com.example.mrMoferBackend.dto.ToiletRequestDto;
import com.example.mrMoferBackend.dto.ToiletResponseDto;
import com.example.mrMoferBackend.entity.Reservation;
import com.example.mrMoferBackend.entity.ReservationDetails;
import com.example.mrMoferBackend.entity.Toilet;
import com.example.mrMoferBackend.entity.User;
import com.example.mrMoferBackend.enums.ReservationStatus;
import com.example.mrMoferBackend.mapper.ReservationMapper;
import com.example.mrMoferBackend.mapper.ToiletMapper;
import com.example.mrMoferBackend.repository.ReservationDetailsRepository;
import com.example.mrMoferBackend.repository.ReservationRepository;
import com.example.mrMoferBackend.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationDetailsRepository reservationDetailsRepository;
    private final ReservationMapper reservationMapper;
    private final ToiletMapper toiletMapper;
    private final UserRepository userRepository;
    private final ToiletService toiletService;

    public ReservationRequestDto createReservation(ReservationRequestDto reservationRequestDto) {

        Reservation reservation = reservationMapper.toEntity(reservationRequestDto);
        reservation.setStatus(ReservationStatus.ACTIVE);
        reservation.setCreatedDate(LocalDate.now());

        User user = userRepository.findByPhoneNumber(reservationRequestDto.getPhoneNumber());

        if (user == null) {
            User newUser = new User();
            newUser.setName(reservationRequestDto.getClientName());
            newUser.setPhoneNumber(reservationRequestDto.getPhoneNumber());
            newUser.addReservation(reservation);
            User userSaved = userRepository.save(newUser);
            reservation.setUser(userSaved);
        }else {
            reservation.setUser(user);
        }

        Reservation savedReservation = reservationRepository.save(reservation);


        for (int i = 0; i < reservationRequestDto.getDetails().size(); i++){

        ReservationDetails reservationDetails = new ReservationDetails();
        reservationDetails.setReservation(reservation);
        reservationDetails.setQuantity(reservationRequestDto.getDetails().get(i).getQuantity());

        ToiletResponseDto toilet = toiletService.getById(reservationRequestDto.getDetails().get(i).getToiletTypeId());
        Toilet reservedToilet = toiletMapper.toEntity(toilet);
        reservationDetails.setToilet(reservedToilet);
        reservationDetailsRepository.save(reservationDetails);
        }

        return reservationMapper.toRequestDto(savedReservation);
    }

    public List<ReservationRequestDto> getReservations(){
        List<Reservation> reservations = reservationRepository.findAll();
        return reservations.stream().map(r -> reservationMapper.toRequestDto(r)).collect(Collectors.toList());
    }

}
