package com.example.demo.repository;

import com.example.demo.entity.FlightTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface FlightTimeRepo extends JpaRepository<FlightTime, Long> {

    FlightTime findByTime (LocalTime time);

    boolean existsByTime(LocalTime time);
}
