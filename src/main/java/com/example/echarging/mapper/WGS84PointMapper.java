package com.example.echarging.mapper;

import com.example.echarging.domain.WGS84Point;
import com.example.echarging.dto.WGS84PointDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WGS84PointMapper {

    WGS84PointDTO toDTO(WGS84Point point);

    WGS84Point toEntity(WGS84PointDTO pointDTO);
}
