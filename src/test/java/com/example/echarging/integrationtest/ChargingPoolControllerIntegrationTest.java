package com.example.echarging.integrationtest;

import com.example.echarging.domain.ChargingPool;
import com.example.echarging.domain.ChargingStation;
import com.example.echarging.domain.PoolLocation;
import com.example.echarging.domain.WGS84Point;
import com.example.echarging.dto.*;
import com.example.echarging.repository.ChargingPoolRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChargingPoolControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ChargingPoolRepository chargingPoolRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        chargingPoolRepository.deleteAll();
    }

    @Test
    public void testGetAllChargingPools() throws Exception {
        ChargingPool pool1 = new ChargingPool();
        pool1.setName("Pool 1");
        pool1.setLocation("Location 1");
        pool1.setCapacity(5);
        pool1.setDcsPoolId("POOL:123");

        PoolLocation location1 = new PoolLocation();
        location1.setCity("City");
        location1.setCountryCode("DE");
        location1.setStreet("Main St");
        location1.setHouseNumber("1");
        location1.setZipCode("12345");

        WGS84Point coordinates = new WGS84Point();
        coordinates.setLatitude(48.1351);
        coordinates.setLongitude(11.5820);
        location1.setCoordinates(coordinates);

        ChargingStation station1 = new ChargingStation();
        station1.setDcsCsId("STATION:001");

        pool1.setPoolLocations(List.of(location1));
        pool1.setChargingStations(List.of(station1));
        chargingPoolRepository.save(pool1);

        ChargingPool pool2 = new ChargingPool();
        pool2.setName("Pool 2");
        pool2.setLocation("Location 2");
        pool2.setCapacity(10);
        pool2.setDcsPoolId("POOL:456");

        PoolLocation location2 = new PoolLocation();
        location2.setCity("City 2");
        location2.setCountryCode("DE");
        location2.setStreet("2nd St");
        location2.setHouseNumber("2");
        location2.setZipCode("67890");

        WGS84Point coordinates2 = new WGS84Point();
        coordinates.setLatitude(48.1351);
        coordinates.setLongitude(11.5820);
        location2.setCoordinates(coordinates);

        ChargingStation station2 = new ChargingStation();
        station2.setDcsCsId("STATION:002");

        pool2.setPoolLocations(List.of(location2));
        pool2.setChargingStations(List.of(station2));
        chargingPoolRepository.save(pool2);

        mockMvc.perform(get("/api/charging-pools")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Pool 1"))
                .andExpect(jsonPath("$[1].name").value("Pool 2"))
                .andExpect(jsonPath("$[0].poolLocations[0].city").value("City"))
                .andExpect(jsonPath("$[1].poolLocations[0].city").value("City 2"));
    }

    @Test
    public void testGetChargingPoolById_Success() throws Exception {
        ChargingPool pool = new ChargingPool();
        pool.setName("Pool 1");
        pool.setLocation("Location 1");
        pool.setCapacity(5);
        pool.setDcsPoolId("POOL:123");

        PoolLocation location = new PoolLocation();
        location.setCity("City");
        location.setCountryCode("DE");
        location.setStreet("Main St");
        location.setHouseNumber("1");
        location.setZipCode("12345");

        WGS84Point coordinates = new WGS84Point();
        coordinates.setLatitude(48.1351);
        coordinates.setLongitude(11.5820);
        location.setCoordinates(coordinates);

        ChargingStation station = new ChargingStation();
        station.setDcsCsId("STATION:001");

        pool.setPoolLocations(List.of(location));
        pool.setChargingStations(List.of(station));

        ChargingPool savedPool = chargingPoolRepository.save(pool);

        mockMvc.perform(get("/api/charging-pools/{id}", savedPool.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Pool 1"))
                .andExpect(jsonPath("$.location").value("Location 1"))
                .andExpect(jsonPath("$.poolLocations[0].city").value("City"))
                .andExpect(jsonPath("$.chargingStations[0].dcsCsId").value("STATION:001"));
    }

    @Test
    public void testGetChargingPoolById_NotFound() throws Exception {
        mockMvc.perform(get("/api/charging-pools/{id}", 999L)  // Assuming this ID does not exist
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].detail").value("Charging pool not found with id: 999"))
                .andExpect(jsonPath("$.errors[0].status").value("404"));
    }

    @Test
    public void testCreateChargingPool_Success() throws Exception {
        ChargingPoolDTO poolDTO = new ChargingPoolDTO();
        poolDTO.setName("New Pool");
        poolDTO.setLocation("New Location");
        poolDTO.setCapacity(8);
        poolDTO.setAccess("PUBLIC");
        poolDTO.setPoolLocationType("ONSTREET");

        PoolLocationDTO locationDTO = new PoolLocationDTO();
        locationDTO.setCity("City");
        locationDTO.setCountryCode("DE");
        locationDTO.setStreet("Main St");
        locationDTO.setHouseNumber("1");
        locationDTO.setZipCode("12345");

        WGS84PointDTO coordinatesDTO = new WGS84PointDTO();
        coordinatesDTO.setLatitude(48.1351);
        coordinatesDTO.setLongitude(11.5820);
        locationDTO.setCoordinates(coordinatesDTO);

        ChargingStationDTO stationDTO = new ChargingStationDTO();
        stationDTO.setDcsCsId("STATION:001");

        poolDTO.setPoolLocations(List.of(locationDTO));
        poolDTO.setChargingStations(List.of(stationDTO));

        mockMvc.perform(post("/api/charging-pools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(poolDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Pool"))
                .andExpect(jsonPath("$.location").value("New Location"))
                .andExpect(jsonPath("$.capacity").value(8))
                .andExpect(jsonPath("$.poolLocations[0].city").value("City"))
                .andExpect(jsonPath("$.poolLocations[0].coordinates.latitude").value(48.1351))
                .andExpect(jsonPath("$.poolLocations[0].coordinates.longitude").value(11.5820))
                .andExpect(jsonPath("$.chargingStations[0].dcsCsId").value("STATION:001"));
    }

    @Test
    public void testCreateChargingPool_ValidationFailure() throws Exception {
        ChargingPoolDTO poolDTO = new ChargingPoolDTO();
        poolDTO.setName("");  // Invalid name
        poolDTO.setLocation("New Location");
        poolDTO.setCapacity(8);

        mockMvc.perform(post("/api/charging-pools")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(poolDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[*].detail").value(Matchers.hasItems(
                        "Field 'name' rejected value []: Name is mandatory",
                        "Field 'poolLocationType' rejected value [null]: Pool location type is mandatory",
                        "Field 'access' rejected value [null]: Access is mandatory"
                )))
                .andExpect(jsonPath("$.errors[*].status").value(Matchers.hasItem("400")));
    }

    @Test
    public void testUpdateChargingPool_Success() throws Exception {
        ChargingPool pool = new ChargingPool();
        pool.setName("Old Pool");
        pool.setLocation("Old Location");
        pool.setCapacity(5);
        pool.setDcsPoolId("POOL:123");
        ChargingPool savedPool = chargingPoolRepository.save(pool);

        ChargingPoolDTO updatedPoolDTO = new ChargingPoolDTO();
        updatedPoolDTO.setName("Updated Pool");
        updatedPoolDTO.setLocation("Updated Location");
        updatedPoolDTO.setCapacity(10);

        PoolLocationDTO locationDTO = new PoolLocationDTO();
        locationDTO.setCity("City Updated");
        locationDTO.setCountryCode("DE");
        locationDTO.setStreet("Main St");
        locationDTO.setHouseNumber("1");
        locationDTO.setZipCode("12345");

        WGS84PointDTO coordinates = new WGS84PointDTO();
        coordinates.setLatitude(48.1351);
        coordinates.setLongitude(11.5820);
        locationDTO.setCoordinates(coordinates);

        ChargingStationDTO stationDTO = new ChargingStationDTO();
        stationDTO.setDcsCsId("STATION:001");

        updatedPoolDTO.setPoolLocations(List.of(locationDTO));
        updatedPoolDTO.setChargingStations(List.of(stationDTO));

        mockMvc.perform(put("/api/charging-pools/{id}", savedPool.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPoolDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Pool"))
                .andExpect(jsonPath("$.location").value("Updated Location"))
                .andExpect(jsonPath("$.capacity").value(10))
                .andExpect(jsonPath("$.poolLocations[0].city").value("City Updated"))
                .andExpect(jsonPath("$.chargingStations[0].dcsCsId").value("STATION:001"));
    }

    @Test
    public void testUpdateChargingPool_NotFound() throws Exception {
        ChargingPoolDTO updatedPoolDTO = new ChargingPoolDTO();
        updatedPoolDTO.setName("Updated Pool");
        updatedPoolDTO.setLocation("Updated Location");
        updatedPoolDTO.setCapacity(10);

        mockMvc.perform(put("/api/charging-pools/{id}", 999L)  // Assuming this ID does not exist
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedPoolDTO)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].detail").value("Charging pool not found with id: 999"))
                .andExpect(jsonPath("$.errors[0].status").value("404"));
    }

    @Test
    public void testDeleteChargingPool_Success() throws Exception {
        ChargingPool pool = new ChargingPool();
        pool.setName("Pool to Delete");
        pool.setLocation("Location");
        pool.setCapacity(5);
        ChargingPool savedPool = chargingPoolRepository.save(pool);

        mockMvc.perform(delete("/api/charging-pools/{id}", savedPool.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Optional<ChargingPool> deletedPool = chargingPoolRepository.findById(savedPool.getId());
        assertTrue(deletedPool.isEmpty(), "The charging pool should be deleted");
    }

    @Test
    public void testDeleteChargingPool_NotFound() throws Exception {
        mockMvc.perform(delete("/api/charging-pools/{id}", 999L)  // Assuming this ID does not exist
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].detail").value("Charging pool not found with id: 999"))
                .andExpect(jsonPath("$.errors[0].status").value("404"));
    }

    @Test
    public void testPoolSearchAndFilter_Success() throws Exception {
        ChargingPool pool1 = new ChargingPool();
        pool1.setName("Pool 1");
        pool1.setLocation("Location 1");
        pool1.setCapacity(5);
        pool1.setDcsPoolId("POOL:123");

        PoolLocation location1 = new PoolLocation();
        location1.setCity("City");
        location1.setCountryCode("DE");
        location1.setStreet("Main St");
        location1.setHouseNumber("1");
        location1.setZipCode("12345");

        WGS84Point coordinates = new WGS84Point();
        coordinates.setLatitude(48.1351);
        coordinates.setLongitude(11.5820);
        location1.setCoordinates(coordinates);

        ChargingStation station1 = new ChargingStation();
        station1.setDcsCsId("STATION:001");

        pool1.setPoolLocations(List.of(location1));
        pool1.setChargingStations(List.of(station1));
        chargingPoolRepository.save(pool1);

        PoolSearchRequest searchRequest = new PoolSearchRequest();
        searchRequest.setDcsPoolIds(List.of("POOL:123"));

        mockMvc.perform(post("/api/charging-pools/pool-find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].dcsPoolId").value("POOL:123"))
                .andExpect(jsonPath("$[0].name").value("Pool 1"))
                .andExpect(jsonPath("$[0].poolLocations[0].city").value("City"))
                .andExpect(jsonPath("$[0].chargingStations[0].dcsCsId").value("STATION:001"));
    }

    @Test
    public void testPoolSearchAndFilter_Failure_InvalidPoolId() throws Exception {
        // Assume the repository is empty or does not contain the pool ID
        PoolSearchRequest searchRequest = new PoolSearchRequest();
        searchRequest.setDcsPoolIds(List.of("POOL:INVALID"));

        mockMvc.perform(post("/api/charging-pools/pool-find")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(searchRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0)); // Expecting no results
    }
}