package com.example.demo.repository;

import com.example.demo.entity.BookedFlight;
import com.example.demo.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedFlightRepo extends JpaRepository<BookedFlight, Long> {

    List<BookedFlight> findByFlight(Flight flight);

    BookedFlight findByUniqueKey(String key);
}
