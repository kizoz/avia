package com.example.demo.service;

import com.example.demo.dto.FlightTimeDTO;
import com.example.demo.dto.IN.BookFlightInPayload;
import com.example.demo.dto.DestinationDTO;
import com.example.demo.dto.IN.FlightInPayload;
import com.example.demo.dto.OUT.BookFlightOutPayload;
import com.example.demo.dto.OUT.FlightInfo;
import com.example.demo.dto.OUT.FlightOutPayload;
import com.example.demo.dto.OUT.FlightTimeOutPayload;
import com.example.demo.entity.BookedFlight;
import com.example.demo.entity.Destination;
import com.example.demo.entity.Flight;
import com.example.demo.entity.FlightTime;
import com.example.demo.enums.FlightClass;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.example.demo.enums.FlightClass.BUSINESS;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class FlightService {

    private final DestinationRepo destinationRepo;

    private final FlightRepo flightRepo;

    private final FlightTimeRepo flightTimeRepo;

    private final UserRepo userRepo;

    private final BookedFlightRepo bookedFlightRepo;

    private final CacheManager cacheManager;

    @Cacheable(value = "flights")
    public List<FlightOutPayload> getFlights(){
        log.info("User gets all available flights");
        return flightRepo.findAll().stream().map(this::convertToOutPayload).collect(Collectors.toList());
    }

    public List<FlightOutPayload> getFlightByDestinationAndTime (String destination, LocalTime time, LocalDate date){
        log.info("User gets flights with destination: {}, time: {} and date: {}", destination, time, date);
        if(Objects.nonNull(time)) {
            return flightRepo.findByFlightTimeAndDateAndDestination(flightTimeRepo.findByTime(time),
                    date, destinationRepo.findByDestination(destination))
                    .stream().map(this::convertToOutPayload).collect(Collectors.toList());
        } else {
            return flightRepo.findByDateAndDestination(date, destinationRepo.findByDestination(destination))
                    .stream().map(this::convertToOutPayload).collect(Collectors.toList());
        }
    }

    public void bookFlight (BookFlightInPayload inPayload, String name){
        Flight flight = flightRepo.findByUniqueId(inPayload.getUniqueId());

        if((flight.getCapacity()
                - bookedFlightRepo.findByFlight(flight).stream().map(BookedFlight::getPlaces).mapToInt(Integer::intValue).sum()
                - inPayload.getPlaces()) < 0)
            throw new IllegalArgumentException("Not enough places in this plane");

        String key = UUID.randomUUID().toString();
        BookedFlight bookedFlight = new BookedFlight();
        bookedFlight.setFlight(flight)
                .setPlaces(inPayload.getPlaces())
                .setUser(userRepo.findByName(name))
                .setUniqueKey(key);
        log.info("User books a flight with key: {}", key);
        bookedFlightRepo.save(bookedFlight);
    }

    @Cacheable(value = "flight-info")
    public FlightInfo flightInfo(){
        return new FlightInfo(getFlightTime(), getDestinations(), getFlights());
    }

    public void createFlight(FlightInPayload inPayload){

        if(Objects.isNull(destinationRepo.findByDestination(inPayload.getDestination())))
            throw new IllegalArgumentException("Invalid destination place");

        if(inPayload.getFlightDate().isBefore(LocalDate.now()))
            throw new IllegalArgumentException("Illegal date");

        Flight flight = new Flight();
        if(inPayload.getIsBigPlane())
            flight.setCapacity(10);
        else flight.setCapacity(5);

        String key = UUID.randomUUID().toString();
        flight.setDate(inPayload.getFlightDate())
                .setDestination(destinationRepo.findByDestination(inPayload.getDestination()))
                .setFlightTime(flightTimeRepo.findByTime(inPayload.getFlightTime()))
                .setUniqueId(key);

        if(inPayload.getIsBusinessClass())
            flight.setFlightClass(BUSINESS);
        else flight.setFlightClass(FlightClass.DEFAULT);

        log.info("User creates new flight with id: {}", key);
        flightRepo.save(flight);
    }

    public void createFlightTime(FlightTimeDTO inPayload){
        if(flightTimeRepo.existsByTime(inPayload.getTime()))
            throw new IllegalArgumentException("This flight time already exist");

        FlightTime flightTime = new FlightTime();
        flightTime.setTime(inPayload.getTime());

        log.info("User creates new flight time: {}", inPayload.getTime().toString());
        flightTimeRepo.save(flightTime);
    }

    public void createDestination(DestinationDTO inPayload){

        if (destinationRepo.existsByDestination(inPayload.getDestination()))
            throw new IllegalArgumentException("This destination place already exist");

        Destination destination = new Destination();
        destination.setDestination(inPayload.getDestination());

        log.info("User register new destination place: {}", inPayload.getDestination());
        destinationRepo.save(destination);
    }

    public List<FlightTimeOutPayload> getFlightTime(){
        log.info("User gets all registered flight times");

        return flightTimeRepo.findAll().stream().map(flightTime -> {
            FlightTimeOutPayload outPayload = new FlightTimeOutPayload();
            return outPayload.setTime(flightTime.getTime());
        }).sorted().collect(Collectors.toList());
    }

    public List<DestinationDTO> getDestinations(){
        log.info("User gets all registered destination places");
        return destinationRepo.findAll().stream().map(destination -> {
            DestinationDTO outPayload = new DestinationDTO();
            return outPayload.setDestination(destination.getDestination());
        }).sorted().collect(Collectors.toList());
    }

    public BookFlightOutPayload getBookFlightOutPayload(String key){
        BookedFlight bookedFlight = bookedFlightRepo.findByUniqueKey(key);
        log.info("User gets ticket by uniques key");
        BookFlightOutPayload outPayload = new BookFlightOutPayload();
        outPayload.setFlight(convertToOutPayload(bookedFlight.getFlight()))
                .setPlaces(bookedFlight.getPlaces())
                .setUserName(bookedFlight.getUser().getName());
        return outPayload;
    }

    private FlightOutPayload convertToOutPayload (Flight flight){

        FlightOutPayload outPayload = new FlightOutPayload();
        outPayload.setDestination(flight.getDestination().getDestination())
                .setFlightClass(flight.getFlightClass().name())
                .setFlightTime(flight.getFlightTime())
                .setUniqueId(flight.getUniqueId());
        return outPayload;
    }

}
