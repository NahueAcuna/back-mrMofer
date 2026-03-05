package com.example.mrMoferBackend.service;

import com.example.mrMoferBackend.dto.dashboard.KpiDto;
import com.example.mrMoferBackend.dto.dashboard.MonthlyReservationsDto;
import com.example.mrMoferBackend.dto.dashboard.MonthlyToiletTypeReservedDto;
import com.example.mrMoferBackend.dto.dashboard.MonthlyTrendDto;
import com.example.mrMoferBackend.interfaces.MonthlyReservations;
import com.example.mrMoferBackend.interfaces.MonthlyToiletTypeReserved;
import com.example.mrMoferBackend.interfaces.MonthlyTrendProjection;
import com.example.mrMoferBackend.repository.ReservationRepository;
import com.example.mrMoferBackend.repository.ToiletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final ReservationRepository reservationRepository;
    private final ToiletRepository toiletRepository;

    public KpiDto getKpi() {
        KpiDto kpiDto = new KpiDto();

        // -----------------------------------------------------------
        // 1. Nuevos clientes
        // -----------------------------------------------------------
        Long currentClients = reservationRepository.getNewClients();
        Long previousClients = reservationRepository.previousMonth_getNewClients();

        kpiDto.setNewClients(currentClients);
        kpiDto.setPreviousMonth_getNewClients(calcularCrecimiento(currentClients, previousClients));

        // -----------------------------------------------------------
        // 2. Ingresos del mes
        // -----------------------------------------------------------
        Double currentRevenue = reservationRepository.getMonthlyRevenue();
        Double previousRevenue = reservationRepository.getPrevious_MonthlyRevenue();

        // Protegemos contra nulos por si el repositorio devuelve null al no haber ventas
        currentRevenue = (currentRevenue != null) ? currentRevenue : 0.0;
        previousRevenue = (previousRevenue != null) ? previousRevenue : 0.0;

        kpiDto.setMonthlyRevenue(currentRevenue);
        kpiDto.setGetPrevious_MonthlyRevenue(calcularCrecimiento(currentRevenue, previousRevenue));

        // -----------------------------------------------------------
        // 3. Reservas activas
        // -----------------------------------------------------------
        Long currentReservations = reservationRepository.getReservationThisMonth();
        Long previousReservations = reservationRepository.getPreviousMonth_Reservations();

        kpiDto.setActiveReservationToday(reservationRepository.getActiveReservations());
        kpiDto.setPreviousMonth_Reservations(calcularCrecimiento(currentReservations, previousReservations));

        // -----------------------------------------------------------
        // 4. Porcentaje baños ocupados
        // -----------------------------------------------------------
        Integer occupied = reservationRepository.currentToiletsReserved();
        Integer total = toiletRepository.totalStock();

        // Protegemos contra nulos
        occupied = (occupied != null) ? occupied : 0;

        double occupiedPercent = 0.0;
        if (total != null && total > 0) {
            occupiedPercent = (occupied * 100.0) / total;
        }
        kpiDto.setOccupiedStockPercentage(occupiedPercent);

        // Comparativa baños ocupados mes pasado
        Long previousOccupied = reservationRepository.PreviousMonth_ToiletsReserved();
        kpiDto.setPreviousMonth_ReservedToilets(calcularCrecimiento(occupied, previousOccupied));

        return kpiDto;
    }

    // ======================================================================
    // MÉTODO HELPER: Calcula la Tasa de Variación de forma segura
    // ======================================================================
    private Double calcularCrecimiento(double actual, double anterior) {
        if (anterior == 0) {
            // Si el mes pasado fue 0 y este mes hay algo, el crecimiento es del 100%
            return actual > 0 ? 100.0 : 0.0;
        }
        // Fórmula real y segura: ((Actual - Anterior) / Anterior) * 100
        return ((actual - anterior) / anterior) * 100.0;
    }

    // ======================================================================
    // MÉTODO PARA EL GRÁFICO DE LÍNEAS
    // ======================================================================
    public List<MonthlyTrendDto> getRevenueTrends() {

        List<MonthlyTrendDto> monthlyTrend = new ArrayList<>();
        List<MonthlyTrendProjection> monthlyInterface = reservationRepository.getRevenueTrends();

        // Verificamos que la lista no esté vacía ni sea nula
        if (monthlyInterface != null && !monthlyInterface.isEmpty()) {

            // Recorremos los datos que llegaron de la base de datos
            for (MonthlyTrendProjection projection : monthlyInterface) {

                // ¡AQUÍ ESTABA EL ERROR 500!
                // Ahora creamos un DTO nuevo por cada vuelta, lo llenamos y lo agregamos a la lista
                MonthlyTrendDto dto = new MonthlyTrendDto();

                dto.setMonth(projection.getMonth());
                dto.setRevenue(projection.getRevenue());

                monthlyTrend.add(dto);
            }
        }

        return monthlyTrend;
    }
    public List<MonthlyReservationsDto> getMonthlyReservations() {
        List<MonthlyReservations> monthlyReservations = reservationRepository.getReservations();
        List<MonthlyReservationsDto> monthlyReservationsDtos = new ArrayList<>();

        for(MonthlyReservations reservation : monthlyReservations){
            MonthlyReservationsDto dto = new MonthlyReservationsDto();
            dto.setMonth(reservation.getMonth());
            dto.setReservations(reservation.getReservations());
            monthlyReservationsDtos.add(dto);
        }
        return monthlyReservationsDtos;

    }

    public List<MonthlyToiletTypeReservedDto> getToiletTypeReserved(){
        List<MonthlyToiletTypeReserved> monthlyToiletTypeReserveds = reservationRepository.getToiletTypeReserved();
        List<MonthlyToiletTypeReservedDto> monthlyToiletTypeReservedDtos =  new ArrayList<>();

        for(MonthlyToiletTypeReserved m: monthlyToiletTypeReserveds){

            MonthlyToiletTypeReservedDto dto = new MonthlyToiletTypeReservedDto();
            dto.setToiletName(m.getToiletName());
            dto.setQuantity(m.getQuantity());
            monthlyToiletTypeReservedDtos.add(dto);
        }
        return monthlyToiletTypeReservedDtos;
    }
    public List<String> toiletsTypes(){
        List<String> toiletsType = reservationRepository.ToiletsType();
        return toiletsType;
    }
}