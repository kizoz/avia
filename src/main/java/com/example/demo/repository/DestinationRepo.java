package com.example.demo.repository;

import com.example.demo.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DestinationRepo extends JpaRepository<Destination, Long> {

    Destination findByDestination (String destination);

    boolean existsByDestination(String destination);
}
