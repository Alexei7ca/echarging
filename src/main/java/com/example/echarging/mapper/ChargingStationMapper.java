package com.example.echarging.mapper;

import com.example.echarging.domain.ChargingStation;
import com.example.echarging.dto.ChargingStationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ChargePointDetailsMapper.class})
public interface ChargingStationMapper {

    @Mapping(target = "chargePoints", source = "chargePoints")
    ChargingStationDTO toDTO(ChargingStation chargingStation);

    @Mapping(target = "chargePoints", source = "chargePoints")
    ChargingStation toEntity(ChargingStationDTO chargingStationDTO);
}
