package com.example.echarging.service;

import com.example.echarging.domain.ChargingPool;
import com.example.echarging.dto.ChargingPoolDTO;
import com.example.echarging.dto.PoolSearchRequest;
import com.example.echarging.dto.PoolSearchResponse;
import com.example.echarging.exception.NotFoundException;
import com.example.echarging.mapper.ChargingPoolMapper;
import com.example.echarging.repository.ChargingPoolRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChargingPoolService {
    private final ChargingPoolRepository repository;
    private final ChargingPoolMapper mapper;

    public ChargingPoolService(ChargingPoolRepository repository, ChargingPoolMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ChargingPoolDTO> getAllChargingPools() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    public ChargingPoolDTO getChargingPoolById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new NotFoundException("Charging pool not found with id: " + id));
    }

    public ChargingPoolDTO createChargingPool(ChargingPoolDTO chargingPoolDTO) {
        //Assume that the combination of name and location must be unique
        if (repository.existsByNameAndLocation(chargingPoolDTO.getName(), chargingPoolDTO.getLocation())) {
            throw new IllegalStateException("A charging pool with the same name and location already exists.");
        }

        ChargingPool chargingPool = mapper.toEntity(chargingPoolDTO);
        ChargingPool savedPool = repository.save(chargingPool);
        return mapper.toDTO(savedPool);
    }

    public ChargingPoolDTO updateChargingPool(Long id, ChargingPoolDTO chargingPoolDTO) {
        ChargingPool existingPool = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Charging pool not found with id: " + id));

        ChargingPool updatedPool = mapper.toEntity(chargingPoolDTO);
        updatedPool.setId(existingPool.getId()); // Preserve the original ID
        ChargingPool savedPool = repository.save(updatedPool);
        return mapper.toDTO(savedPool);
    }

    public void deleteChargingPool(Long id) {
        ChargingPool existingPool = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Charging pool not found with id: " + id));
        repository.delete(existingPool);
    }

    public List<PoolSearchResponse> poolSearchAndFilter(PoolSearchRequest request) {
        List<ChargingPool> pools = repository.findPoolsByIds(request.getDcsPoolIds());
        return pools.stream()
                .map(mapper::toDTO)
                .map(this::mapToPoolSearchResponse)
                .collect(Collectors.toList());
    }

    private PoolSearchResponse mapToPoolSearchResponse(ChargingPoolDTO chargingPoolDTO) {
        PoolSearchResponse response = new PoolSearchResponse();
        response.setDcsPoolId(chargingPoolDTO.getDcsPoolId());
        response.setName(chargingPoolDTO.getName());
        response.setIncomingPoolId(chargingPoolDTO.getIncomingPoolId());
        response.setOpen24h(chargingPoolDTO.isOpen24h());
        response.setAccess(chargingPoolDTO.getAccess());
        response.setPoolLocationType(chargingPoolDTO.getPoolLocationType());
        response.setPoolLocations(chargingPoolDTO.getPoolLocations());
        response.setChargingStations(chargingPoolDTO.getChargingStations());
        return response;
    }
}