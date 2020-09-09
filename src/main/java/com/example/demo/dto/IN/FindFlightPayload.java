package com.example.demo.dto.IN;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class FindFlightPayload {

    private String destination;

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
}
