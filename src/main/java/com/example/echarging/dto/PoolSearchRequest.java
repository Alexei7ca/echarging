package com.example.echarging.dto;

import lombok.Data;

import java.util.List;

@Data
public class PoolSearchRequest {
    private List<String> dcsPoolIds;

    private PoolFilter filterCriteria;
}
