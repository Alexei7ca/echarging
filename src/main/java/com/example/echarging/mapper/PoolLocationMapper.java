package com.example.echarging.mapper;

import com.example.echarging.domain.PoolLocation;
import com.example.echarging.dto.PoolLocationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PoolLocationMapper {

    PoolLocationDTO toDTO(PoolLocation poolLocation);

    PoolLocation toEntity(PoolLocationDTO poolLocationDTO);
}
