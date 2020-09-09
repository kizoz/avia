package com.example.demo.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;

@Data
public class FlightTimeDTO {

    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime time;
}
