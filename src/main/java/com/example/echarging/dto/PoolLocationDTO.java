package com.example.echarging.dto;

import lombok.Data;

@Data
public class PoolLocationDTO {
    private Long id;

    private String city;
    private String countryCode; // ISO 3166 Alpha-2 code
    private String houseNumber;
    private String state;
    private String street;
    private String type;  // ENTRY, UNKNOWN
    private String zipCode;

    private WGS84PointDTO coordinates;
}
