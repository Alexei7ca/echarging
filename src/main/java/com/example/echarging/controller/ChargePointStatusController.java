package com.example.echarging.controller;

import com.example.echarging.dto.ChargePointDynStatusRequest;
import com.example.echarging.dto.ChargePointDynStatusResponseList;
import com.example.echarging.service.ChargePointStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/charge-point-status")
public class ChargePointStatusController {
    private final ChargePointStatusService service;

    public ChargePointStatusController(ChargePointStatusService service) {
        this.service = service;
    }

    @GetMapping("/{chargePointId}")
    public ResponseEntity<Object> getChargePointStatus(@PathVariable String chargePointId) {
        Object status = service.getChargePointStatus(chargePointId);
        return ResponseEntity.ok(status);
    }

    @PostMapping("/{chargePointId}")
    public ResponseEntity<Void> createChargePointStatus(@PathVariable String chargePointId, @RequestBody Object status) {
        service.createChargePointStatus(chargePointId, status);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{chargePointId}")
    public ResponseEntity<Void> deleteChargePointStatus(@PathVariable String chargePointId) {
        service.deleteChargePointStatus(chargePointId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/availability/charge-points")
    public ResponseEntity<ChargePointDynStatusResponseList> getDynamicPoiData(
            @Valid @RequestBody ChargePointDynStatusRequest request) {
        ChargePointDynStatusResponseList response = service.getDynamicPoiData(request);
        return ResponseEntity.ok(response);
    }
}
