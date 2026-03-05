package com.example.mrMoferBackend.repository;

import com.example.mrMoferBackend.entity.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ToiletRepository extends JpaRepository<Toilet,Long> {

    boolean existsByName(String name);
    @Query(value = "SELECT COALESCE(SUM(stock),0) FROM toilet", nativeQuery = true)
    Integer totalStock();
}
