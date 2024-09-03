package com.example.echarging.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "charging_stations")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ChargingStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dcsCsId;
    private String incomingCsId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "charging_station_auth_methods", joinColumns = @JoinColumn(name = "charging_station_id"))
    @Column(name = "auth_method")
    private List<String> chargingStationAuthMethods;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "charging_station_id")
    private List<ChargePointDetails> chargePoints;
}
