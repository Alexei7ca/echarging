package com.example.echarging.integrationtest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ChargePointStatusControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    public void setUp() {
        redisTemplate.getConnectionFactory().getConnection().flushDb();
    }

    @Test
    public void testGetChargePointStatus_Success() throws Exception {
        String chargePointId = "CP1";
        String status = "Available";
        redisTemplate.opsForValue().set(chargePointId, status);

        mockMvc.perform(get("/api/charge-point-status/{chargePointId}", chargePointId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(status));
    }

    @Test
    public void testGetChargePointStatus_NotFound() throws Exception {
        String chargePointId = "CP2";

        mockMvc.perform(get("/api/charge-point-status/{chargePointId}", chargePointId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].detail").value("Charge point status not found for id: " + chargePointId))
                .andExpect(jsonPath("$.errors[0].status").value("404"));
    }

    @Test
    public void testCreateChargePointStatus_Success() throws Exception {
        String chargePointId = "CP3";
        String status = "Charging";

        mockMvc.perform(post("/api/charge-point-status/{chargePointId}", chargePointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + status + "\""))  // Directly passing the string status
                .andExpect(status().isCreated());

        Object savedStatus = redisTemplate.opsForValue().get(chargePointId);
        assertEquals(status, savedStatus);
    }

    @Test
    public void testCreateChargePointStatus_AlreadyExists() throws Exception {
        String chargePointId = "CP3";
        String status = "Charging";
        redisTemplate.opsForValue().set(chargePointId, status);

        mockMvc.perform(post("/api/charge-point-status/{chargePointId}", chargePointId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("\"" + status + "\""))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.errors[0].detail").value("Status already exists for charge point ID: " + chargePointId))
                .andExpect(jsonPath("$.errors[0].status").value("409"));
    }

    @Test
    public void testDeleteChargePointStatus_Success() throws Exception {
        String chargePointId = "CP4";
        String status = "Available";
        redisTemplate.opsForValue().set(chargePointId, status);

        mockMvc.perform(delete("/api/charge-point-status/{chargePointId}", chargePointId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Boolean exists = redisTemplate.hasKey(chargePointId);
        Assertions.assertFalse(exists != null && exists);
    }

    @Test
    public void testDeleteChargePointStatus_NotFound() throws Exception {
        String chargePointId = "CP5";

        mockMvc.perform(delete("/api/charge-point-status/{chargePointId}", chargePointId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors[0].detail").value("Charge point status not found for id: " + chargePointId))
                .andExpect(jsonPath("$.errors[0].status").value("404"));
    }

    @Test
    public void testGetDynamicPoiData_Success() throws Exception {
        String chargePointId1 = "CHARGE_POINT:948ebcb1-59da-32ed-a2c4-f45b4a515a35";
        String chargePointId2 = "CHARGE_POINT:200a4e22-b996-3cd0-8505-7cf6f5690714";
        String status1 = "CHARGING";
        String status2 = "AVAILABLE";

        redisTemplate.opsForValue().set(chargePointId1, status1);
        redisTemplate.opsForValue().set(chargePointId2, status2);

        String requestContent = "{\"chargePointIds\": [\"" + chargePointId1 + "\", \"" + chargePointId2 + "\"]}";

        mockMvc.perform(post("/api/charge-point-status/availability/charge-points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chargePointDynStatusResponses[0].chargePointID").value(chargePointId1))
                .andExpect(jsonPath("$.chargePointDynStatusResponses[0].operationalState").value(status1))
                .andExpect(jsonPath("$.chargePointDynStatusResponses[1].chargePointID").value(chargePointId2))
                .andExpect(jsonPath("$.chargePointDynStatusResponses[1].operationalState").value(status2));
    }

    @Test
    public void testGetDynamicPoiData_InvalidChargePoints() throws Exception {
        String chargePointId1 = "CHARGE_POINT:invalid1";
        String chargePointId2 = "CHARGE_POINT:invalid2";

        String requestContent = "{\"chargePointIds\": [\"" + chargePointId1 + "\", \"" + chargePointId2 + "\"]}";

        mockMvc.perform(post("/api/charge-point-status/availability/charge-points")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.chargePointDynStatusResponsestatus.status").value(1000))
                .andExpect(jsonPath("$.chargePointDynStatusResponsestatus.description").value("Success"))
                .andExpect(jsonPath("$.chargePointDynStatusResponsestatus.invalidChargePointIDs").isEmpty());
    }
}