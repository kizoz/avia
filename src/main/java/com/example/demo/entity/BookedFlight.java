package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BookedFlight {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;

    private Integer places;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String uniqueKey;
}
