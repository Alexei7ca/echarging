package com.example.echarging.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChargingStationDTO {
    private Long id;

    private String dcsCsId;
    private String incomingCsId;

    private List<String> chargingStationAuthMethods;

    private List<ChargePointDetailsDTO> chargePoints;
}
