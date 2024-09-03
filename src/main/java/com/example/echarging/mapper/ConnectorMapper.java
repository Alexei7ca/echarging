package com.example.echarging.mapper;

import com.example.echarging.domain.Connector;
import com.example.echarging.dto.ConnectorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConnectorMapper {

    ConnectorDTO toDTO(Connector connector);

    Connector toEntity(ConnectorDTO connectorDTO);
}
