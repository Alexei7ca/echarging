package com.example.echarging.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChargePointDynStatusResponsestatus {
    private int status;
    private String description;
    private List<String> invalidChargePointIDs;
}
