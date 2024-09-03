package com.example.echarging.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "charge_point_details")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class ChargePointDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dcsCpId;
    private String incomingCpId;
    private boolean dynamicInfoAvailable;
    private boolean isonormedID;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "charge_point_details_id")
    private List<Connector> connectors;
}
