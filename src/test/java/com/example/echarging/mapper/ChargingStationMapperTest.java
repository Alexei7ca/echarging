package com.example.echarging.mapper;

import com.example.echarging.domain.ChargingStation;
import com.example.echarging.dto.ChargingStationDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChargingStationMapperTest {

    private final ChargingStationMapper mapper = Mappers.getMapper(ChargingStationMapper.class);

    @Test
    public void testToDTO() {
        ChargingStation station = new ChargingStation();
        station.setDcsCsId("Station1");
        station.setChargingStationAuthMethods(List.of("REMOTE"));

        ChargingStationDTO stationDTO = mapper.toDTO(station);

        assertNotNull(stationDTO, "The mapped DTO should not be null");
        assertEquals(station.getDcsCsId(), stationDTO.getDcsCsId(), "The station IDs should match");
        assertEquals(station.getChargingStationAuthMethods(), stationDTO.getChargingStationAuthMethods(), "The auth methods should match");
    }

    @Test
    public void testToEntity() {
        ChargingStationDTO stationDTO = new ChargingStationDTO();
        stationDTO.setDcsCsId("Station1");
        stationDTO.setChargingStationAuthMethods(List.of("REMOTE"));

        ChargingStation station = mapper.toEntity(stationDTO);

        assertNotNull(station, "The mapped entity should not be null");
        assertEquals(stationDTO.getDcsCsId(), station.getDcsCsId(), "The station IDs should match");
        assertEquals(stationDTO.getChargingStationAuthMethods(), station.getChargingStationAuthMethods(), "The auth methods should match");
    }
}
