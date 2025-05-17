package com.coolCompany.sights_on_map_api.controller;

import com.coolCompany.sights_on_map_api.dto.SightResponseDTO;
import com.coolCompany.sights_on_map_api.repository.SightRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sight")
@RequiredArgsConstructor
public class SightController {

    private final SightRepository sightRepository;

    @GetMapping("/nearby")
    public List<SightResponseDTO> getSightsNearby(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam double radiusMeters,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minAverageRating,
            @RequestParam(required = false, defaultValue = "10") Integer limit
    ) {
        return sightRepository.findSightsNearby(
                        latitude,
                        longitude,
                        radiusMeters,
                        category,
                        minAverageRating,
                        limit
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

    @GetMapping("/name/{name}")
    public List<SightResponseDTO> getByName(
            @PathVariable String name,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) Double minRating
    ) {
        return sightRepository.findByNameFiltered(name, category, minRating).stream()
                .map(SightResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}
