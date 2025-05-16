package com.coolCompany.sights_on_map_api.controller;

import com.coolCompany.sights_on_map_api.dto.FeedbackDTO;
import com.coolCompany.sights_on_map_api.dto.SightDTO;
import com.coolCompany.sights_on_map_api.dto.SightResponseDTO;
import com.coolCompany.sights_on_map_api.entity.FeedbackEntity;
import com.coolCompany.sights_on_map_api.entity.SightEntity;
import com.coolCompany.sights_on_map_api.repository.FeedbackRepository;
import com.coolCompany.sights_on_map_api.repository.SightRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sight")
@RequiredArgsConstructor
public class SightController {

    private final SightRepository sightRepository;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @GetMapping
    public List<SightResponseDTO> getAllSights() {
        return sightRepository.findAll().stream()
                .map(SightResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public SightResponseDTO getSight(@PathVariable Long id) {
        return sightRepository.findById(id)
                .map(SightResponseDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Sight not found"));
    }

    @PostMapping
    public SightResponseDTO createSight(@RequestBody SightDTO dto) {
        Point point = geometryFactory.createPoint(new Coordinate(dto.getLongitude(), dto.getLatitude()));
        SightEntity sight = new SightEntity();
        sight.setName(dto.getName());
        sight.setCity(dto.getCity());
        sight.setCategory(dto.getCategory());
        sight.setPosition(point);
        sightRepository.save(sight);
        return SightResponseDTO.fromEntity(sight);
    }

    @GetMapping("/city/{city}")
    public List<SightResponseDTO> getSightsByCity(@PathVariable String city) {
        return sightRepository.findByCityIgnoreCase(city).stream()
                .map(SightResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }
}

