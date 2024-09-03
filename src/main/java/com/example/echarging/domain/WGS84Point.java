package com.example.echarging.domain;

import lombok.*;

import javax.persistence.*;

@Embeddable
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class WGS84Point {
    private double latitude;
    private double longitude;
}
