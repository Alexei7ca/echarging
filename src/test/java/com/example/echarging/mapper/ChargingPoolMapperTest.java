package com.example.echarging.mapper;

import com.example.echarging.domain.*;
import com.example.echarging.dto.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ChargingPoolMapperTest {

    @Autowired
    private ChargingPoolMapper mapper;


    @Test
    public void testToDTO() {
        ChargingPool pool = new ChargingPool();
        pool.setId(1L);
        pool.setName("Test Pool");
        pool.setLocation("Test Location");
        pool.setCapacity(10);

        PoolLocation location = new PoolLocation();
        location.setCity("City");
        location.setCountryCode("DE");
        location.setStreet("Main St");
        location.setHouseNumber("1");
        location.setZipCode("12345");
        pool.setPoolLocations(List.of(location));

        ChargingStation station = new ChargingStation();
        station.setDcsCsId("Station1");
        station.setChargingStationAuthMethods(List.of("REMOTE"));
        ChargePointDetails chargePoint = new ChargePointDetails();
        chargePoint.setDcsCpId("CP1");
        chargePoint.setConnectors(List.of(new Connector(null, "CHADEMO", 50))); //null as id for testing
        station.setChargePoints(List.of(chargePoint));
        pool.setChargingStations(List.of(station));

        ChargingPoolDTO poolDTO = mapper.toDTO(pool);

        assertNotNull(poolDTO, "The mapped DTO should not be null");
        assertEquals(pool.getId(), poolDTO.getId(), "The IDs should match");
        assertEquals(pool.getName(), poolDTO.getName(), "The names should match");
        assertEquals(pool.getLocation(), poolDTO.getLocation(), "The locations should match");
        assertEquals(pool.getCapacity(), poolDTO.getCapacity(), "The capacities should match");

        // Test nested objects
        assertNotNull(poolDTO.getPoolLocations(), "Pool locations should not be null");
        assertEquals(1, poolDTO.getPoolLocations().size(), "There should be one pool location");
        assertEquals(location.getCity(), poolDTO.getPoolLocations().get(0).getCity(), "The city should match");

        assertNotNull(poolDTO.getChargingStations(), "Charging stations should not be null");
        assertEquals(1, poolDTO.getChargingStations().size(), "There should be one charging station");
        assertEquals(station.getDcsCsId(), poolDTO.getChargingStations().get(0).getDcsCsId(), "The station ID should match");
    }

    @Test
    public void testToEntity() {
        ChargingPoolDTO poolDTO = new ChargingPoolDTO();
        poolDTO.setId(1L);
        poolDTO.setName("Test Pool");
        poolDTO.setLocation("Test Location");
        poolDTO.setCapacity(10);

        PoolLocationDTO locationDTO = new PoolLocationDTO();
        locationDTO.setCity("City");
        locationDTO.setCountryCode("DE");
        locationDTO.setStreet("Main St");
        locationDTO.setHouseNumber("1");
        locationDTO.setZipCode("12345");
        poolDTO.setPoolLocations(List.of(locationDTO));

        ChargingStationDTO stationDTO = new ChargingStationDTO();
        stationDTO.setDcsCsId("Station1");
        stationDTO.setChargingStationAuthMethods(List.of("REMOTE"));
        ChargePointDetailsDTO chargePointDTO = new ChargePointDetailsDTO();
        chargePointDTO.setDcsCpId("CP1");
        // Using the no-args constructor and setting the fields manually for connector
        ConnectorDTO connectorDTO = new ConnectorDTO();
        connectorDTO.setPlugType("CHADEMO");
        connectorDTO.setPowerLevel(50);

        chargePointDTO.setConnectors(List.of(connectorDTO));
        stationDTO.setChargePoints(List.of(chargePointDTO));
        poolDTO.setChargingStations(List.of(stationDTO));

        ChargingPool pool = mapper.toEntity(poolDTO);

        assertNotNull(pool, "The mapped entity should not be null");
        assertEquals(poolDTO.getId(), pool.getId(), "The IDs should match");
        assertEquals(poolDTO.getName(), pool.getName(), "The names should match");
        assertEquals(poolDTO.getLocation(), pool.getLocation(), "The locations should match");
        assertEquals(poolDTO.getCapacity(), pool.getCapacity(), "The capacities should match");

        // Test nested objects
        assertNotNull(pool.getPoolLocations(), "Pool locations should not be null");
        assertEquals(1, pool.getPoolLocations().size(), "There should be one pool location");
        assertEquals(locationDTO.getCity(), pool.getPoolLocations().get(0).getCity(), "The city should match");

        assertNotNull(pool.getChargingStations(), "Charging stations should not be null");
        assertEquals(1, pool.getChargingStations().size(), "There should be one charging station");
        assertEquals(stationDTO.getDcsCsId(), pool.getChargingStations().get(0).getDcsCsId(), "The station ID should match");
    }

    @Test
    public void testNullEntityToDTO() {
        ChargingPoolDTO poolDTO = mapper.toDTO(null);
        assertNull(poolDTO, "Mapping a null entity should return a null DTO");
    }

    @Test
    public void testNullDTOToEntity() {
        ChargingPool pool = mapper.toEntity(null);
        assertNull(pool, "Mapping a null DTO should return a null entity");
    }
}