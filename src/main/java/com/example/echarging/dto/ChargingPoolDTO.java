package com.example.echarging.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;

import lombok.Data;

import java.util.List;

@Data
public class ChargingPoolDTO {
    private Long id;

    private String dcsPoolId;
    private String incomingPoolId;
    private boolean open24h;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Location is mandatory")
    private String location;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    @NotBlank(message = "Access is mandatory")
    private String access;  // PUBLIC, PRIVATE

    @NotBlank(message = "Pool location type is mandatory")
    private String poolLocationType;  // ONSTREET, UNKNOWN

    private List<PoolLocationDTO> poolLocations;
    private List<ChargingStationDTO> chargingStations;
}
