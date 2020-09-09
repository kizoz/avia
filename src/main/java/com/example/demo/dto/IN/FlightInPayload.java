package com.example.demo.dto.IN;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class FlightInPayload {

    private String  destination;

    private Boolean isBigPlane = false;

    private Boolean isBusinessClass = false;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate flightDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime flightTime;
}
