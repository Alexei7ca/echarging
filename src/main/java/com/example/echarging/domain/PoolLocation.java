package com.example.echarging.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pool_locations")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PoolLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String countryCode; // ISO 3166 Alpha-2 code
    private String houseNumber;
    private String state;
    private String street;
    private String type;  // ENTRY, UNKNOWN
    private String zipCode;

    @Embedded
    private WGS84Point coordinates;
}
