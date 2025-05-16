package com.coolCompany.sights_on_map_api.repository;

import com.coolCompany.sights_on_map_api.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<FeedbackEntity, Long> {
    List<FeedbackEntity> findBySightId(Long sightId);
}
