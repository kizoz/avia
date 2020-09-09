package com.example.demo.dto.OUT;

import com.example.demo.entity.FlightTime;
import lombok.Data;

@Data
public class FlightOutPayload {

    private String  destination;

    private String  flightClass;

    private Integer places;

    private FlightTime flightTime;

    private String userName;

    private String uniqueId;
}
