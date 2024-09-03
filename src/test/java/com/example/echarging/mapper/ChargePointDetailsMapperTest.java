package com.example.echarging.mapper;

import com.example.echarging.domain.ChargePointDetails;
import com.example.echarging.dto.ChargePointDetailsDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class ChargePointDetailsMapperTest {

    private final ChargePointDetailsMapper mapper = Mappers.getMapper(ChargePointDetailsMapper.class);

    @Test
    public void testToDTO() {
        ChargePointDetails details = new ChargePointDetails();
        details.setDcsCpId("CP1");
        details.setDynamicInfoAvailable(true);

        ChargePointDetailsDTO detailsDTO = mapper.toDTO(details);

        assertNotNull(detailsDTO, "The mapped DTO should not be null");
        assertEquals(details.getDcsCpId(), detailsDTO.getDcsCpId(), "The CP IDs should match");
        assertEquals(details.isDynamicInfoAvailable(), detailsDTO.isDynamicInfoAvailable(), "The dynamic info availability should match");
    }

    @Test
    public void testToEntity() {
        ChargePointDetailsDTO detailsDTO = new ChargePointDetailsDTO();
        detailsDTO.setDcsCpId("CP1");
        detailsDTO.setDynamicInfoAvailable(true);

        ChargePointDetails details = mapper.toEntity(detailsDTO);

        assertNotNull(details, "The mapped entity should not be null");
        assertEquals(detailsDTO.getDcsCpId(), details.getDcsCpId(), "The CP IDs should match");
        assertEquals(detailsDTO.isDynamicInfoAvailable(), details.isDynamicInfoAvailable(), "The dynamic info availability should match");
    }
}