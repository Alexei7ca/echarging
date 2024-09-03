package com.example.echarging.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChargePointDynStatusResponseList {
    private List<ChargePointDynStatusResponse> chargePointDynStatusResponses;
    private ChargePointDynStatusResponsestatus chargePointDynStatusResponsestatus;
}
