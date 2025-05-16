package com.coolCompany.sights_on_map_api.controller;

import com.coolCompany.sights_on_map_api.dto.FeedbackDTO;
import com.coolCompany.sights_on_map_api.entity.FeedbackEntity;
import com.coolCompany.sights_on_map_api.entity.SightEntity;
import com.coolCompany.sights_on_map_api.repository.FeedbackRepository;
import com.coolCompany.sights_on_map_api.repository.SightRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
class FeedbackController {

    private final FeedbackRepository feedbackRepository;
    private final SightRepository sightRepository;

    @GetMapping
    public List<FeedbackEntity> getAllFeedback() {
        return feedbackRepository.findAll();
    }

    @GetMapping("/{id}")
    public FeedbackEntity getFeedback(@PathVariable Long id) {
        return feedbackRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Feedback not found"));
    }

    @GetMapping("/{id}/feedback")
    public List<FeedbackEntity> getFeedbackForSight(@PathVariable Long id) {
        return feedbackRepository.findBySightId(id);
    }

    @PostMapping
    public FeedbackEntity createFeedback(@RequestBody FeedbackDTO dto) {
        SightEntity sight = sightRepository.findById(dto.getSightId())
                .orElseThrow(() -> new EntityNotFoundException("Sight not found"));

        FeedbackEntity feedback = new FeedbackEntity();
        feedback.setSight(sight);
        feedback.setUsername(dto.getUsername());
        feedback.setText(dto.getText());
        feedback.setEstimation(dto.getEstimation());

        return feedbackRepository.save(feedback);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Long id) {
        if (!feedbackRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        feedbackRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
