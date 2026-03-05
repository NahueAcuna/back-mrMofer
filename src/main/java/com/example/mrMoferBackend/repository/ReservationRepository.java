package com.example.mrMoferBackend.repository;

import com.example.mrMoferBackend.entity.Reservation;
import com.example.mrMoferBackend.interfaces.MonthlyReservations;
import com.example.mrMoferBackend.interfaces.MonthlyToiletTypeReserved;
import com.example.mrMoferBackend.interfaces.MonthlyTrendProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    //------------------------------------------------------------ KPIS ---------------------------------------------------------------------
    // El status 3 es el 'ACTIVE'
    @Query(value = "SELECT COALESCE(COUNT(*),0) FROM reservation r WHERE r.status = 3", nativeQuery = true)
    Long getActiveReservations();

    // MODIFICADO: Todo el mes pasado completo
    @Query(value = "SELECT COALESCE(COUNT(*),0) FROM reservation r WHERE MONTH(r.created_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "AND YEAR(r.created_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)", nativeQuery = true)
    Long getPreviousMonth_Reservations();

    // MODIFICADO: Agregada la validación del año actual para evitar cruces con años futuros
    @Query(value = "SELECT COALESCE(COUNT(*),0) FROM reservation r WHERE MONTH(r.created_date) = MONTH(CURRENT_DATE) " +
            "AND YEAR(r.created_date) = YEAR(CURRENT_DATE)", nativeQuery = true)
    Long getReservationThisMonth();

    //---------------------------------------------
    @Query(value = "SELECT COALESCE(SUM(r.price),0) FROM reservation r WHERE MONTH(r.start_date) = MONTH(CURRENT_DATE)" +
            " AND YEAR(r.start_date) = YEAR(CURRENT_DATE)", nativeQuery = true)
    Double getMonthlyRevenue();

    @Query(value = "SELECT COALESCE(SUM(r.price),0) FROM reservation r WHERE MONTH(r.start_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)" +
            " AND YEAR(r.start_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)", nativeQuery = true)
    Double getPrevious_MonthlyRevenue();

    //---------------------------------------------

    // Cantidad de baños reservados hoy (Activos)
    @Query(value = "SELECT COALESCE(SUM(d.quantity),0) FROM reservation r JOIN reservation_details d ON r.id = d.reservation_id WHERE r.status = 3", nativeQuery = true)
    Integer currentToiletsReserved();

    // MODIFICADO: Todo el mes pasado completo
    @Query(value = "SELECT COALESCE(SUM(d.quantity),0) FROM reservation r JOIN reservation_details d ON r.id = d.reservation_id " +
            "WHERE MONTH(r.start_date) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "AND YEAR(r.start_date) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)", nativeQuery = true)
    Long PreviousMonth_ToiletsReserved();

    //---------------------------------------------

    @Query(value = "SELECT COUNT(*) FROM (" +
            "SELECT user_id FROM reservation " +
            "GROUP BY user_id " +
            "HAVING MONTH(MIN(start_date)) = MONTH(CURRENT_DATE) " +
            "AND YEAR(MIN(start_date)) = YEAR(CURRENT_DATE)" +
            ") AS nuevos_clientes", nativeQuery = true)
    Long getNewClients();

    // MODIFICADO: Todo el mes pasado completo
    @Query(value = "SELECT COUNT(*) FROM (" +
            "SELECT user_id FROM reservation " +
            "GROUP BY user_id " +
            "HAVING MONTH(MIN(start_date)) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "AND YEAR(MIN(start_date)) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH)" +
            ") AS nuevos_clientes", nativeQuery = true)
    Long previousMonth_getNewClients();

    //------------------------------------------------------------ Grafico de Lineas ---------------------------------------------------------------------

    @Query(value = "SELECT MONTH(start_date) as month, SUM(price) as revenue " +
            "FROM reservation " +
            "WHERE YEAR(start_date) = YEAR(CURRENT_DATE) " +
            "GROUP BY MONTH(start_date) " +
            "ORDER BY MONTH(start_date) ASC", nativeQuery = true)
    List<MonthlyTrendProjection> getRevenueTrends();

    @Query(value = "SELECT MONTH(start_date) AS month, COALESCE(COUNT(*),0) AS reservations FROM reservation " +
            "WHERE YEAR(start_date) = YEAR(CURRENT_DATE) " +
            "GROUP BY MONTH(start_date) " +
            "ORDER BY MONTH(start_date) ASC", nativeQuery = true)
    List<MonthlyReservations> getReservations();

    //------------------------------------------------------------ Grafico de dona ---------------------------------------------------------------------

    @Query(value = "SELECT t.name AS toiletName, COALESCE(SUM(d.quantity),0) AS quantity FROM reservation r " +
            "JOIN reservation_details d ON r.id = d.reservation_id JOIN toilet t ON d.toilet_id = t.id " +
            "WHERE MONTH(r.start_date) = MONTH(CURRENT_DATE) AND " +
            "YEAR(start_date) = YEAR(CURRENT_DATE) " +
            "GROUP BY t.name", nativeQuery = true)
    List<MonthlyToiletTypeReserved> getToiletTypeReserved();

    @Query(value = "SELECT name FROM toilet", nativeQuery = true)
    List<String> ToiletsType();

}