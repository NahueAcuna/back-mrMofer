package com.example.mrMoferBackend.repository;

import com.example.mrMoferBackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface UserRepository extends JpaRepository<User,Long> {

}
