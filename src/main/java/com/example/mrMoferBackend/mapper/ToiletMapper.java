package com.example.mrMoferBackend.mapper;

import com.example.mrMoferBackend.dto.ToiletRequestDto;
import com.example.mrMoferBackend.dto.ToiletResponseDto;
import com.example.mrMoferBackend.entity.Toilet;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToiletMapper {

    ToiletRequestDto toRequestDto(Toilet toilet);
    ToiletResponseDto toResponseDto(Toilet toilet);
    Toilet toEntity(ToiletRequestDto toiletRequestDto);
}
