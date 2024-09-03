package com.example.echarging.mapper;

import com.example.echarging.domain.ChargingPool;
import com.example.echarging.dto.ChargingPoolDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PoolLocationMapper.class, ChargingStationMapper.class})
public interface ChargingPoolMapper {


    @Mapping(target = "poolLocations", source = "poolLocations")
    @Mapping(target = "chargingStations", source = "chargingStations")
    @Mapping(target = "name", source = "name")
    ChargingPoolDTO toDTO(ChargingPool chargingPool);

    @Mapping(target = "poolLocations", source = "poolLocations")
    @Mapping(target = "chargingStations", source = "chargingStations")
    @Mapping(target = "name", source = "name")
    ChargingPool toEntity(ChargingPoolDTO chargingPoolDTO);
}
