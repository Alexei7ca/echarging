package com.example.echarging.mapper;

import com.example.echarging.domain.PoolLocation;
import com.example.echarging.dto.PoolLocationDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class PoolLocationMapperTest {

    private final PoolLocationMapper mapper = Mappers.getMapper(PoolLocationMapper.class);

    @Test
    public void testToDTO() {
        PoolLocation location = new PoolLocation();
        location.setCity("City");
        location.setCountryCode("DE");
        location.setStreet("Main St");
        location.setHouseNumber("1");
        location.setZipCode("12345");

        PoolLocationDTO locationDTO = mapper.toDTO(location);

        assertNotNull(locationDTO, "The mapped DTO should not be null");
        assertEquals(location.getCity(), locationDTO.getCity(), "The cities should match");
        assertEquals(location.getCountryCode(), locationDTO.getCountryCode(), "The country codes should match");
    }

    @Test
    public void testToEntity() {
        PoolLocationDTO locationDTO = new PoolLocationDTO();
        locationDTO.setCity("City");
        locationDTO.setCountryCode("DE");
        locationDTO.setStreet("Main St");
        locationDTO.setHouseNumber("1");
        locationDTO.setZipCode("12345");

        PoolLocation location = mapper.toEntity(locationDTO);

        assertNotNull(location, "The mapped entity should not be null");
        assertEquals(locationDTO.getCity(), location.getCity(), "The cities should match");
        assertEquals(locationDTO.getCountryCode(), location.getCountryCode(), "The country codes should match");
    }
}
