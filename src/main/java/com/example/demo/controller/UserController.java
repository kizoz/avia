package com.example.demo.controller;

import com.example.demo.dto.IN.BookFlightInPayload;
import com.example.demo.dto.IN.FindFlightPayload;
import com.example.demo.dto.OUT.BookFlightOutPayload;
import com.example.demo.dto.OUT.FlightInfo;
import com.example.demo.dto.OUT.FlightOutPayload;
import com.example.demo.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class UserController {

    private final FlightService flightService;

    @PostMapping
    public ResponseEntity<Void> bookFlight(@RequestBody BookFlightInPayload inPayload){
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        flightService.bookFlight(inPayload, userName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/all-flights")
    public ResponseEntity<List<FlightOutPayload>> getAllFlights(){
        return new ResponseEntity<>(flightService.getFlights(), HttpStatus.OK);
    }

    @GetMapping("/book-info")
    public ResponseEntity<FlightInfo> getAllTimes(){
        return new ResponseEntity<>(flightService.flightInfo(), HttpStatus.OK);
    }

    @GetMapping("/ticket/{key}")
    public ResponseEntity<BookFlightOutPayload> getTicket(@PathVariable("key") String key){
        return new ResponseEntity<>(flightService.getBookFlightOutPayload(key), HttpStatus.OK);
    }

    @GetMapping("/look-for-flight")
    public ResponseEntity<List<FlightOutPayload>> getFlightByDestinationAndTime(@RequestBody FindFlightPayload payload){
        return new ResponseEntity<>(flightService
                .getFlightByDestinationAndTime(payload.getDestination(), payload.getTime(), payload.getDate()),
                HttpStatus.OK);
    }
}
