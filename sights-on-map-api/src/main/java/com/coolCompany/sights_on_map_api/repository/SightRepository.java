package com.coolCompany.sights_on_map_api.repository;

import com.coolCompany.sights_on_map_api.entity.SightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface SightRepository extends JpaRepository<SightEntity, Long> {
    List<SightEntity> findByCityIgnoreCase(String city);
}
