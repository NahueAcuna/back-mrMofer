package com.example.mrMoferBackend.repository;

import com.example.mrMoferBackend.entity.Toilet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ToiletRepository extends JpaRepository<Toilet,Long> {

    boolean existsByName(String name);
}
