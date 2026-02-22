package com.example.mrMoferBackend.dto.dashboard;

import lombok.Data;

@Data
public class MonthlyTrendDto {
    private String month;
    private Double revenue;
}
