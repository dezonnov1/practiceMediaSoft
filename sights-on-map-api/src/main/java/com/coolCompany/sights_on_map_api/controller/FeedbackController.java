package com.coolCompany.sights_on_map_api.controller;

import com.coolCompany.sights_on_map_api.dto.FeedbackDTO;
import com.coolCompany.sights_on_map_api.dto.FeedbackResponseDTO;
import com.coolCompany.sights_on_map_api.entity.FeedbackEntity;
import com.coolCompany.sights_on_map_api.entity.SightEntity;
import com.coolCompany.sights_on_map_api.repository.FeedbackRepository;
import com.coolCompany.sights_on_map_api.repository.SightRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final SightRepository sightRepository;

    @PostMapping
    public FeedbackResponseDTO createFeedback(@RequestBody @Valid FeedbackDTO dto) {
        SightEntity sight;

        if (dto.getSightId() != null) {
            sight = sightRepository.findById(dto.getSightId())
                    .orElseThrow(() -> new EntityNotFoundException("Sight not found by id"));
        } else if (dto.getSightName() != null && !dto.getSightName().isBlank()) {
            sight = sightRepository.findByNameIgnoreCase(dto.getSightName())
                    .orElseThrow(() -> new EntityNotFoundException("Sight not found by name"));
        } else {
            throw new IllegalArgumentException("Either sightId or sightName must be provided");
        }

        FeedbackEntity feedback = new FeedbackEntity();
        feedback.setSight(sight);
        feedback.setUsername(dto.getUsername());
        feedback.setText(dto.getText());
        feedback.setEstimation(dto.getEstimation());

        return FeedbackResponseDTO.fromEntity(feedbackRepository.save(feedback));
    }

    @GetMapping("/all")
    public List<FeedbackResponseDTO> getAllFeedback() {
        return feedbackRepository.findAll()
                .stream()
                .map(FeedbackResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/id/{id}")
    public FeedbackResponseDTO getFeedback(@PathVariable Long id) {
        return feedbackRepository.findById(id)
                .map(FeedbackResponseDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
    }

    @GetMapping("/by-sight")
    public List<FeedbackResponseDTO> getFeedbackBySight(
            @RequestParam(required = false) Long sightId,
            @RequestParam(required = false) String sightName) {

        if (sightId == null && (sightName == null || sightName.isBlank())) {
            throw new IllegalArgumentException("Either sightId or sightName must be provided");
        }

        SightEntity sightById = null;
        SightEntity sightByName = null;

        if (sightId != null) {
            sightById = sightRepository.findById(sightId)
                    .orElseThrow(() -> new EntityNotFoundException("Sight not found with id: " + sightId));
        }

        if (sightName != null && !sightName.isBlank()) {
            sightByName = sightRepository.findByNameIgnoreCase(sightName)
                    .orElseThrow(() -> new EntityNotFoundException("Sight not found with name: " + sightName));
        }

        SightEntity sight;

        if (sightById != null && sightByName != null) {
            // Проверяем, совпадают ли id и имя
            if (!sightById.getId().equals(sightByName.getId())) {
                throw new IllegalArgumentException("Sight id and name do not match");
            }
            sight = sightById;
        } else if (sightById != null) {
            sight = sightById;
        } else {
            sight = sightByName;
        }

        List<FeedbackEntity> feedbackList = feedbackRepository.findBySightId(sight.getId());

        return feedbackList.stream()
                .map(FeedbackResponseDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        if (!feedbackRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        feedbackRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
