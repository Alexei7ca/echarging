package com.example.echarging.mapper;

import com.example.echarging.domain.Connector;
import com.example.echarging.dto.ConnectorDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class ConnectorMapperTest {

    private final ConnectorMapper mapper = Mappers.getMapper(ConnectorMapper.class);

    @Test
    public void testToDTO() {
        Connector connector = new Connector();
        connector.setPlugType("CHADEMO");
        connector.setPowerLevel(50);

        ConnectorDTO connectorDTO = mapper.toDTO(connector);

        assertNotNull(connectorDTO, "The mapped DTO should not be null");
        assertEquals(connector.getPlugType(), connectorDTO.getPlugType(), "The plug types should match");
        assertEquals(connector.getPowerLevel(), connectorDTO.getPowerLevel(), "The power levels should match");
    }

    @Test
    public void testToEntity() {
        ConnectorDTO connectorDTO = new ConnectorDTO();
        connectorDTO.setPlugType("CHADEMO");
        connectorDTO.setPowerLevel(50);

        Connector connector = mapper.toEntity(connectorDTO);

        assertNotNull(connector, "The mapped entity should not be null");
        assertEquals(connectorDTO.getPlugType(), connector.getPlugType(), "The plug types should match");
        assertEquals(connectorDTO.getPowerLevel(), connector.getPowerLevel(), "The power levels should match");
    }
}
