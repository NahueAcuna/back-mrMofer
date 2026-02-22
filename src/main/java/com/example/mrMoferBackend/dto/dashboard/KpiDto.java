package com.example.mrMoferBackend.dto.dashboard;

import lombok.Data;

@Data
public class KpiDto {
    private Long activeReservationToday;
    private Long monthlyRevenue;
    private Double occupiedStockPercentage;
    private Integer newClients;
}
