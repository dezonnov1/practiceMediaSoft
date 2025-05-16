package com.coolCompany.sights_on_map_api.controller;

import com.coolCompany.sights_on_map_api.dto.NearbySightRequest;
import com.coolCompany.sights_on_map_api.dto.SightResponseDTO;
import com.coolCompany.sights_on_map_api.entity.FeedbackEntity;
import com.coolCompany.sights_on_map_api.entity.SightEntity;
import com.coolCompany.sights_on_map_api.repository.FeedbackRepository;
import com.coolCompany.sights_on_map_api.repository.SightRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sight")
@RequiredArgsConstructor
public class SightController {

    private final SightRepository sightRepository;
    private final FeedbackRepository feedbackRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @PostMapping("/nearby")
    public List<SightResponseDTO> getSightsNearby(@RequestBody NearbySightRequest request) {
        return sightRepository.findSightsNearby(
                        request.getLatitude(),
                        request.getLongitude(),
                        request.getRadiusMeters(),
                        request.getCategory(),
                        request.getMinAverageRating(),
                        request.getLimit() != null ? request.getLimit() : 10
                ).stream()
                .map(SightResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/city/{city}")
    public List<SightResponseDTO> getSightsByCity(
            @PathVariable String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minRating
    ) {
        return sightRepository.findSightsByCity(city, category, minRating).stream()
                .map(SightResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/name/{name}/rating")
    public Map<String, Object> getSightRating(@PathVariable String name) {
        SightEntity sight = sightRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("Sight not found"));
        List<FeedbackEntity> feedbacks = feedbackRepository.findBySightId(sight.getId());
        double avg = feedbacks.stream().mapToInt(FeedbackEntity::getEstimation).average().orElse(0.0);

        Map<String, Object> result = new HashMap<>();
        result.put("sight", SightResponseDTO.fromEntity(sight));
        result.put("averageRating", avg);
        return result;
    }

    @GetMapping("/name/{name}/feedbacks")
    public List<FeedbackEntity> getSightFeedbacks(@PathVariable String name) {
        SightEntity sight = sightRepository.findByNameIgnoreCase(name)
                .orElseThrow(() -> new EntityNotFoundException("Sight not found"));
        return feedbackRepository.findBySightId(sight.getId());
    }
}