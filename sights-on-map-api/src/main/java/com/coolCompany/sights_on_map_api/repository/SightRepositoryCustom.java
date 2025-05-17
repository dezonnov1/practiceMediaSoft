package com.coolCompany.sights_on_map_api.repository;

import com.coolCompany.sights_on_map_api.entity.SightEntity;

import java.util.List;

public interface SightRepositoryCustom {
    List<SightEntity> findSightsNearby(double latitude, double longitude, double radiusMeters,
                                       String category, Double minAverageRating, int limit);

    List<SightEntity> findSightsByCity(String city, String category, Double minAverageRating);

    List<SightEntity> findByNameFiltered(String name, String category, Double minAverageRating);
}
