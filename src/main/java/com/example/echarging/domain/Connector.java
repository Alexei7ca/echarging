package com.example.echarging.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "connectors")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Connector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String plugType;  // CHADEMO, UNKNOWN
    private int powerLevel;   // e.g., 350

}
