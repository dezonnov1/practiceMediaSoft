package com.coolCompany.sights_on_map_api.repository;

import com.coolCompany.sights_on_map_api.entity.SightEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface SightRepository extends JpaRepository<SightEntity, Long>, SightRepositoryCustom {
    Optional<SightEntity> findByNameIgnoreCase(String name);
    List<SightEntity> findByCityIgnoreCase(String city);
    Optional<SightEntity> findByName(String name);

}