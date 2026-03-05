package com.example.mrMoferBackend.controller;

import com.example.mrMoferBackend.dto.dashboard.KpiDto;
import com.example.mrMoferBackend.dto.dashboard.MonthlyTrendDto;
import com.example.mrMoferBackend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dashboard")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping("/kpis")
    public ResponseEntity<?> getKpis(){

        KpiDto kpiDto = dashboardService.getKpi();

        return ResponseEntity.ok(kpiDto);
    }

    @GetMapping("/revenue-trends")
    public ResponseEntity<?> getMonthlyrevenue(){

        List<MonthlyTrendDto> monthlyTrendDto = dashboardService.getRevenueTrends();

        return ResponseEntity.ok().body(monthlyTrendDto);
    }
    @GetMapping("/reservations")
    public ResponseEntity<?> getMonthlyReservations(){
        return ResponseEntity.ok().body(dashboardService.getMonthlyReservations());
    }
    @GetMapping("/toilet-type-reserved")
    public ResponseEntity<?> getToiletTypeReserved(){
        return ResponseEntity.ok().body(dashboardService.getToiletTypeReserved());
    }


    @GetMapping("/toilet-types")
    public ResponseEntity<?> getToiletTypes(){
        return ResponseEntity.ok().body(dashboardService.toiletsTypes());
    }

}
