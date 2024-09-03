package com.example.echarging.dto;

import lombok.Data;

import java.util.List;

@Data
public class PoolFilter {
    private List<String> authenticationMethods; // REMOTE, UNKNOWN
}
