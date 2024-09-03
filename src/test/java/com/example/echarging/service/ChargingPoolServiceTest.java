package com.example.echarging.service;

import com.example.echarging.domain.ChargingPool;
import com.example.echarging.dto.ChargingPoolDTO;
import com.example.echarging.dto.PoolSearchRequest;
import com.example.echarging.dto.PoolSearchResponse;
import com.example.echarging.exception.NotFoundException;
import com.example.echarging.mapper.ChargingPoolMapper;
import com.example.echarging.repository.ChargingPoolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ChargingPoolServiceTest {


    @Mock
    private ChargingPoolRepository chargingPoolRepository;

    @Mock
    private ChargingPoolMapper chargingPoolMapper;

    @InjectMocks
    private ChargingPoolService chargingPoolService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllChargingPools() {
        ChargingPool pool = new ChargingPool();
        when(chargingPoolRepository.findAll()).thenReturn(List.of(pool));
        when(chargingPoolMapper.toDTO(pool)).thenReturn(new ChargingPoolDTO());

        List<ChargingPoolDTO> result = chargingPoolService.getAllChargingPools();
        assertEquals(1, result.size());
        verify(chargingPoolRepository, times(1)).findAll();
    }

    @Test
    public void testGetAllChargingPools_Empty() {
        when(chargingPoolRepository.findAll()).thenReturn(List.of());

        List<ChargingPoolDTO> result = chargingPoolService.getAllChargingPools();
        assertTrue(result.isEmpty());
        verify(chargingPoolRepository, times(1)).findAll();
    }


    @Test
    public void testGetChargingPoolById_Success() {
        ChargingPool pool = new ChargingPool();
        ChargingPoolDTO poolDTO = new ChargingPoolDTO();

        when(chargingPoolRepository.findById(1L)).thenReturn(Optional.of(pool));
        when(chargingPoolMapper.toDTO(pool)).thenReturn(poolDTO);

        ChargingPoolDTO result = chargingPoolService.getChargingPoolById(1L);
        assertNotNull(result);
        verify(chargingPoolRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetChargingPoolById_NotFound() {
        when(chargingPoolRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> chargingPoolService.getChargingPoolById(1L));
        verify(chargingPoolRepository, times(1)).findById(1L);
    }

    @Test
    public void testCreateChargingPool() {
        ChargingPool pool = new ChargingPool();
        pool.setId(1L);
        pool.setName("New Pool");
        pool.setLocation("New Location");
        pool.setCapacity(10);

        ChargingPoolDTO poolDTO = new ChargingPoolDTO();
        poolDTO.setName("New Pool");
        poolDTO.setLocation("New Location");
        poolDTO.setCapacity(10);

        when(chargingPoolMapper.toEntity(poolDTO)).thenReturn(pool);
        when(chargingPoolRepository.save(pool)).thenReturn(pool);
        when(chargingPoolMapper.toDTO(pool)).thenReturn(poolDTO);

        ChargingPoolDTO result = chargingPoolService.createChargingPool(poolDTO);

        assertNotNull(result, "The result should not be null");
        assertEquals(poolDTO.getName(), result.getName(), "The name should match");
        assertEquals(poolDTO.getLocation(), result.getLocation(), "The location should match");
        assertEquals(poolDTO.getCapacity(), result.getCapacity(), "The capacity should match");
        verify(chargingPoolRepository, times(1)).save(pool);
    }

    @Test
    public void testCreateChargingPool_AlreadyExists() {
        ChargingPoolDTO poolDTO = new ChargingPoolDTO();
        poolDTO.setName("Test Pool");
        poolDTO.setLocation("Test Location");

        when(chargingPoolRepository.existsByNameAndLocation(poolDTO.getName(), poolDTO.getLocation())).thenReturn(true);

        assertThrows(IllegalStateException.class, () -> chargingPoolService.createChargingPool(poolDTO));
        verify(chargingPoolRepository, never()).save(any(ChargingPool.class));
    }

    @Test
    public void testUpdateChargingPool_Success() {
        ChargingPool existingPool = new ChargingPool();
        existingPool.setId(1L);
        existingPool.setName("Old Pool");
        existingPool.setLocation("Old Location");
        existingPool.setCapacity(5);

        ChargingPoolDTO updatedPoolDTO = new ChargingPoolDTO();
        updatedPoolDTO.setName("Updated Pool");
        updatedPoolDTO.setLocation("Updated Location");
        updatedPoolDTO.setCapacity(15);

        ChargingPool updatedPool = new ChargingPool();
        updatedPool.setId(1L);
        updatedPool.setName("Updated Pool");
        updatedPool.setLocation("Updated Location");
        updatedPool.setCapacity(15);

        when(chargingPoolRepository.findById(1L)).thenReturn(Optional.of(existingPool));
        when(chargingPoolMapper.toEntity(updatedPoolDTO)).thenReturn(updatedPool);
        when(chargingPoolRepository.save(updatedPool)).thenReturn(updatedPool);
        when(chargingPoolMapper.toDTO(updatedPool)).thenReturn(updatedPoolDTO);

        ChargingPoolDTO result = chargingPoolService.updateChargingPool(1L, updatedPoolDTO);

        assertNotNull(result, "The result should not be null");
        assertEquals(updatedPoolDTO.getName(), result.getName(), "The name should be updated");
        assertEquals(updatedPoolDTO.getLocation(), result.getLocation(), "The location should be updated");
        assertEquals(updatedPoolDTO.getCapacity(), result.getCapacity(), "The capacity should be updated");
        verify(chargingPoolRepository, times(1)).findById(1L);
        verify(chargingPoolRepository, times(1)).save(updatedPool);
    }

    @Test
    public void testUpdateChargingPool_NotFound() {
        ChargingPoolDTO poolDTO = new ChargingPoolDTO();

        when(chargingPoolRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> chargingPoolService.updateChargingPool(1L, poolDTO));
        verify(chargingPoolRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteChargingPool_Success() {
        ChargingPool pool = new ChargingPool();

        when(chargingPoolRepository.findById(1L)).thenReturn(Optional.of(pool));

        chargingPoolService.deleteChargingPool(1L);
        verify(chargingPoolRepository, times(1)).findById(1L);
        verify(chargingPoolRepository, times(1)).delete(pool);
    }

    @Test
    public void testDeleteChargingPool_NotFound() {
        when(chargingPoolRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> chargingPoolService.deleteChargingPool(1L));
        verify(chargingPoolRepository, times(1)).findById(1L);
        verify(chargingPoolRepository, never()).delete(any(ChargingPool.class));
    }

    @Test
    public void testPoolSearchAndFilter_Success() {
        PoolSearchRequest request = new PoolSearchRequest();
        request.setDcsPoolIds(List.of("POOL1", "POOL2"));

        ChargingPool pool1 = new ChargingPool();
        pool1.setDcsPoolId("POOL1");
        ChargingPool pool2 = new ChargingPool();
        pool2.setDcsPoolId("POOL2");

        ChargingPoolDTO poolDTO1 = new ChargingPoolDTO();
        poolDTO1.setDcsPoolId("POOL1");
        ChargingPoolDTO poolDTO2 = new ChargingPoolDTO();
        poolDTO2.setDcsPoolId("POOL2");

        when(chargingPoolRepository.findPoolsByIds(request.getDcsPoolIds()))
                .thenReturn(List.of(pool1, pool2));

        when(chargingPoolMapper.toDTO(pool1)).thenReturn(poolDTO1);
        when(chargingPoolMapper.toDTO(pool2)).thenReturn(poolDTO2);

        // Manually map the ChargingPoolDTO to PoolSearchResponse
        PoolSearchResponse response1 = new PoolSearchResponse();
        response1.setDcsPoolId(poolDTO1.getDcsPoolId());
        PoolSearchResponse response2 = new PoolSearchResponse();
        response2.setDcsPoolId(poolDTO2.getDcsPoolId());

        List<PoolSearchResponse> result = chargingPoolService.poolSearchAndFilter(request);

        assertEquals(2, result.size());
        assertEquals("POOL1", result.get(0).getDcsPoolId());
        assertEquals("POOL2", result.get(1).getDcsPoolId());
    }

    @Test
    public void testPoolSearchAndFilter_EmptyResult() {
        PoolSearchRequest request = new PoolSearchRequest();
        request.setDcsPoolIds(List.of("POOL1", "POOL2"));

        when(chargingPoolRepository.findPoolsByIds(request.getDcsPoolIds())).thenReturn(List.of());

        List<PoolSearchResponse> result = chargingPoolService.poolSearchAndFilter(request);

        assertTrue(result.isEmpty());
    }
}
