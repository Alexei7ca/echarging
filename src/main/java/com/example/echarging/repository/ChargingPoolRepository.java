package com.example.echarging.repository;

import com.example.echarging.domain.ChargingPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChargingPoolRepository extends JpaRepository<ChargingPool, Long> {

    boolean existsByNameAndLocation(String name, String location);

    @Query("SELECT cp FROM ChargingPool cp WHERE cp.dcsPoolId IN :dcsPoolIds")
    List<ChargingPool> findPoolsByIds(@Param("dcsPoolIds") List<String> dcsPoolIds);

}
