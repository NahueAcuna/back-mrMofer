package com.example.mrMoferBackend.dto.dashboard;

import lombok.Data;

@Data
public class ToiletTypeStatDto {
    private String toiletTypeName;
    private Long unitCount; //cantidad de veces que fue reservado
}
