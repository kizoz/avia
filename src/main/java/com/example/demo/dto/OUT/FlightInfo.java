package com.example.demo.dto.OUT;

import com.example.demo.dto.DestinationDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FlightInfo {

    private List<FlightTimeOutPayload> flightTimeOutPayloads;

    private List<DestinationDTO> destinationOutPayloads;

    private List<FlightOutPayload> flightOutPayloads;
}
