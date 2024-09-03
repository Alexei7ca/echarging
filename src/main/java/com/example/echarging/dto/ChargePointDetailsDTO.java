package com.example.echarging.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChargePointDetailsDTO {
    private Long id;

    private String dcsCpId;
    private String incomingCpId;
    private boolean dynamicInfoAvailable;
    private boolean isonormedID;

    private List<ConnectorDTO> connectors;
}
