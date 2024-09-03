package com.example.echarging.dto;

import lombok.Data;

@Data
public class ConnectorDTO {
    private Long id;

    private String plugType;  // CHADEMO, UNKNOWN
    private int powerLevel;   // e.g., 350

}
