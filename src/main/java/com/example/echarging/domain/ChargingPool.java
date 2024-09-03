package com.example.echarging.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "charging_pools")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ChargingPool {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dcsPoolId;
    private String incomingPoolId;
    private boolean open24h;
    private String access;  // PUBLIC, PRIVATE
    private String poolLocationType;  // ONSTREET, UNKNOWN

    private String name;
    private String location;
    private int capacity;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "charging_pool_id")
    private List<PoolLocation> poolLocations;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "charging_pool_id")
    private List<ChargingStation> chargingStations;
}
