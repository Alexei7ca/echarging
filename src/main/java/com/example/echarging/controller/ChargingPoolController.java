package com.example.echarging.controller;

import com.example.echarging.dto.ChargingPoolDTO;
import com.example.echarging.dto.PoolSearchRequest;
import com.example.echarging.dto.PoolSearchResponse;
import com.example.echarging.service.ChargingPoolService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/charging-pools")
@Validated
public class ChargingPoolController {
    private final ChargingPoolService service;

    public ChargingPoolController(ChargingPoolService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ChargingPoolDTO>> getAllChargingPools() {
        return ResponseEntity.ok(service.getAllChargingPools());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargingPoolDTO> getChargingPoolById(@PathVariable Long id) {
        ChargingPoolDTO chargingPool = service.getChargingPoolById(id);
        return ResponseEntity.ok(chargingPool);
    }

    @PostMapping
    public ResponseEntity<ChargingPoolDTO> createChargingPool(@Valid @RequestBody ChargingPoolDTO chargingPoolDTO) {
        ChargingPoolDTO createdPool = service.createChargingPool(chargingPoolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPool);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChargingPoolDTO> updateChargingPool(
            @PathVariable Long id, @RequestBody ChargingPoolDTO chargingPoolDTO) {
        ChargingPoolDTO updatedPool = service.updateChargingPool(id, chargingPoolDTO);
        return ResponseEntity.ok(updatedPool);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChargingPool(@PathVariable Long id) {
        service.deleteChargingPool(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/pool-find")
    public ResponseEntity<List<PoolSearchResponse>> poolSearchAndFilter(
            @Valid @RequestBody PoolSearchRequest request) {
        List<PoolSearchResponse> response = service.poolSearchAndFilter(request);
        return ResponseEntity.ok(response);
    }
}
