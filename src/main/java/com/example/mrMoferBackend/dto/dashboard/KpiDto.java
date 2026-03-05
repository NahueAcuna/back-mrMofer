package com.example.mrMoferBackend.dto.dashboard;

import lombok.Data;

@Data
public class KpiDto {
    private Long activeReservationToday;
    private Double PreviousMonth_Reservations;

    private Double monthlyRevenue;
    private Double getPrevious_MonthlyRevenue;

    private Double occupiedStockPercentage;
    private Double PreviousMonth_ReservedToilets;

    private Long newClients;
    private Double previousMonth_getNewClients;
}
