package com.example.demo.entity;

import com.example.demo.enums.Capacity;
import com.example.demo.enums.FlightClass;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Flight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    private Integer capacity;

    private FlightClass flightClass;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private FlightTime flightTime;

    private LocalDate date;

    private String uniqueId;
}
