package com.example.echarging.service;

import com.example.echarging.dto.ChargePointDynStatusRequest;
import com.example.echarging.dto.ChargePointDynStatusResponseList;
import com.example.echarging.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChargePointStatusServiceTest {

    @Mock
    private RedisTemplate<String, Object> redisTemplate;

    @Mock
    private ValueOperations<String, Object> valueOperations;

    @InjectMocks
    private ChargePointStatusService chargePointStatusService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testGetChargePointStatus() {
        String chargePointId = "CP1";
        Object status = "Available";

        when(valueOperations.get(chargePointId)).thenReturn(status);

        Object result = chargePointStatusService.getChargePointStatus(chargePointId);

        assertNotNull(result);
        assertEquals(status, result);
        verify(valueOperations, times(1)).get(chargePointId);
    }

    @Test
    public void testChargePointStatusExists_True() {
        String chargePointId = "CP1";

        when(redisTemplate.hasKey(chargePointId)).thenReturn(true);

        boolean exists = chargePointStatusService.chargePointStatusExists(chargePointId);

        assertTrue(exists);
        verify(redisTemplate, times(1)).hasKey(chargePointId);
    }

    @Test
    public void testChargePointStatusExists_False() {
        String chargePointId = "CP1";

        when(redisTemplate.hasKey(chargePointId)).thenReturn(false);

        boolean exists = chargePointStatusService.chargePointStatusExists(chargePointId);

        assertFalse(exists);
        verify(redisTemplate, times(1)).hasKey(chargePointId);
    }

    @Test
    public void testCreateChargePointStatus_Success() {
        String chargePointId = "CP1";
        Object status = "Available";

        when(redisTemplate.hasKey(chargePointId)).thenReturn(false);

        chargePointStatusService.createChargePointStatus(chargePointId, status);

        verify(valueOperations, times(1)).set(chargePointId, status);
    }

    @Test
    public void testCreateChargePointStatus_AlreadyExists() {
        String chargePointId = "CP1";
        Object status = "Available";

        when(redisTemplate.hasKey(chargePointId)).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> {
            chargePointStatusService.createChargePointStatus(chargePointId, status);
        });

        verify(valueOperations, never()).set(anyString(), any());
    }

    @Test
    public void testDeleteChargePointStatus_Success() {
        String chargePointId = "CP1";

        when(redisTemplate.hasKey(chargePointId)).thenReturn(true);

        chargePointStatusService.deleteChargePointStatus(chargePointId);

        verify(redisTemplate, times(1)).delete(chargePointId);
    }

    @Test
    public void testDeleteChargePointStatus_NotFound() {
        String chargePointId = "CP1";

        when(redisTemplate.hasKey(chargePointId)).thenReturn(false);

        assertThrows(NotFoundException.class, () -> {
            chargePointStatusService.deleteChargePointStatus(chargePointId);
        });

        verify(redisTemplate, never()).delete(chargePointId);
    }


    @Test
    public void testGetDynamicPoiData_Success() {
        ChargePointDynStatusRequest request = new ChargePointDynStatusRequest();
        request.setChargePointIds(List.of("CP1", "CP2"));

        when(valueOperations.get("CP1")).thenReturn("CHARGING");
        when(valueOperations.get("CP2")).thenReturn("AVAILABLE");

        ChargePointDynStatusResponseList result = chargePointStatusService.getDynamicPoiData(request);

        assertNotNull(result);
        assertEquals(2, result.getChargePointDynStatusResponses().size());
        assertEquals("CHARGING", result.getChargePointDynStatusResponses().get(0).getOperationalState());
        assertEquals("AVAILABLE", result.getChargePointDynStatusResponses().get(1).getOperationalState());
    }

    @Test
    public void testGetDynamicPoiData_PartialSuccess() {
        ChargePointDynStatusRequest request = new ChargePointDynStatusRequest();
        request.setChargePointIds(List.of("CP1", "CP3")); // Assume CP3 does not exist

        when(valueOperations.get("CP1")).thenReturn("CHARGING");
        when(valueOperations.get("CP3")).thenReturn(null); // CP3 not found

        ChargePointDynStatusResponseList result = chargePointStatusService.getDynamicPoiData(request);

        assertNotNull(result);
        assertEquals(1, result.getChargePointDynStatusResponses().size());
        assertEquals("CHARGING", result.getChargePointDynStatusResponses().get(0).getOperationalState());
    }
}
