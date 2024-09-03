package com.example.echarging.dto;

import lombok.Data;

@Data
public class ChargePointDynStatusResponse {
    private String chargePointID;
    private String operationalState;  // AVAILABLE, CHARGING, UNKNOWN
    private String timestamp;
}
