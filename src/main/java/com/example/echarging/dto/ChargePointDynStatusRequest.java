package com.example.echarging.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChargePointDynStatusRequest {
    private List<String> chargePointIds;
}
