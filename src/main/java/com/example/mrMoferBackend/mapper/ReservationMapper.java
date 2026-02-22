package com.example.mrMoferBackend.mapper;

import com.example.mrMoferBackend.dto.ReservationRequestDto;
import com.example.mrMoferBackend.dto.ReservationResponseDto;
import com.example.mrMoferBackend.entity.Reservation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReservationMapper {

    ReservationRequestDto toRequestDto(Reservation reservation);
    Reservation toEntity(ReservationRequestDto reservationRequestDto);

}
