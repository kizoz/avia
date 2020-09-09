package com.example.demo.dto.OUT;

import lombok.Data;

@Data
public class BookFlightOutPayload {

    private FlightOutPayload flight;

    private Integer places;

    private String userName;
}
