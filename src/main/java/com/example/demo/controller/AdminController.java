package com.example.demo.controller;

import com.example.demo.dto.DestinationDTO;
import com.example.demo.dto.FlightTimeDTO;
import com.example.demo.dto.IN.FlightInPayload;
import com.example.demo.service.FlightService;
import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/a")
@RequiredArgsConstructor
public class AdminController {

    private final FlightService flightService;

    @PostMapping("/flight")
    public ResponseEntity<Void> createFlight(@RequestBody @NotNull FlightInPayload inPayload){
        flightService.createFlight(inPayload);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/time")
    public ResponseEntity<Void> createTime(@RequestBody @NotNull FlightTimeDTO flightTimeDTO){
        flightService.createFlightTime(flightTimeDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/destination")
    public ResponseEntity<Void> createDestination(@RequestBody @NotNull DestinationDTO destinationDTO){
        flightService.createDestination(destinationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
