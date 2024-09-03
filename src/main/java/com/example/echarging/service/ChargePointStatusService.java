package com.example.echarging.service;

import com.example.echarging.dto.ChargePointDynStatusRequest;
import com.example.echarging.dto.ChargePointDynStatusResponse;
import com.example.echarging.dto.ChargePointDynStatusResponseList;
import com.example.echarging.dto.ChargePointDynStatusResponsestatus;
import com.example.echarging.exception.NotFoundException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ChargePointStatusService {
    private final RedisTemplate<String, Object> redisTemplate;

    public ChargePointStatusService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Object getChargePointStatus(String chargePointId) {
        Object status = redisTemplate.opsForValue().get(chargePointId);
        if (status == null) {
            throw new NotFoundException("Charge point status not found for id: " + chargePointId);
        }
        return status;
    }

    public boolean chargePointStatusExists(String chargePointId) {
        Boolean exists = redisTemplate.hasKey(chargePointId);
        return exists != null && exists;
    }

    public void createChargePointStatus(String chargePointId, Object status) {
        if (chargePointStatusExists(chargePointId)) {
            throw new IllegalStateException("Status already exists for charge point ID: " + chargePointId);
        }
        redisTemplate.opsForValue().set(chargePointId, status);
    }

    public void deleteChargePointStatus(String chargePointId) {
        if (!chargePointStatusExists(chargePointId)) {
            throw new NotFoundException("Charge point status not found for id: " + chargePointId);
        }
        redisTemplate.delete(chargePointId);
    }

    public ChargePointDynStatusResponseList getDynamicPoiData(ChargePointDynStatusRequest request) {
        List<ChargePointDynStatusResponse> responses = new ArrayList<>();

        for (String chargePointId : request.getChargePointIds()) {
            Object status = redisTemplate.opsForValue().get(chargePointId);
            if (status != null) {
                ChargePointDynStatusResponse response = new ChargePointDynStatusResponse();
                response.setChargePointID(chargePointId);
                response.setOperationalState(status.toString());
                response.setTimestamp(LocalDateTime.now().toString());
                responses.add(response);
            }
        }

        ChargePointDynStatusResponsestatus status = new ChargePointDynStatusResponsestatus();
        status.setStatus(1000);  // adjust as needed
        status.setDescription("Success");
        status.setInvalidChargePointIDs(new ArrayList<>()); // Populate if needed

        ChargePointDynStatusResponseList responseList = new ChargePointDynStatusResponseList();
        responseList.setChargePointDynStatusResponses(responses);
        responseList.setChargePointDynStatusResponsestatus(status);

        return responseList;
    }
}