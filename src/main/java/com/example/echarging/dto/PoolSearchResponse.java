package com.example.echarging.dto;

import lombok.Data;

import java.util.List;

@Data
public class PoolSearchResponse {
    private String dcsPoolId;
    private String name;
    private String incomingPoolId;
    private boolean open24h;
    private String access;  // PUBLIC, PRIVATE
    private String poolLocationType;  // ONSTREET, UNKNOWN

    private List<PoolLocationDTO> poolLocations;
    private List<ChargingStationDTO> chargingStations;
}
