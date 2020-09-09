package com.example.demo.repository;

import com.example.demo.entity.Destination;
import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightTime;
import com.example.demo.enums.Capacity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepo extends JpaRepository<Flight, Long> {

    Flight findByUniqueId (String uniqueId);

    List<Flight> findByFlightTimeAndDateAndDestination(FlightTime time, LocalDate date, Destination destination);

    List<Flight> findByDateAndDestination(LocalDate date, Destination destination);
}
