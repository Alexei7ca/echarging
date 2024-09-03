package com.example.echarging.mapper;

import com.example.echarging.domain.ChargePointDetails;
import com.example.echarging.dto.ChargePointDetailsDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ConnectorMapper.class})
public interface ChargePointDetailsMapper {

    ChargePointDetailsDTO toDTO(ChargePointDetails chargePointDetails);

    ChargePointDetails toEntity(ChargePointDetailsDTO chargePointDetailsDTO);
}
