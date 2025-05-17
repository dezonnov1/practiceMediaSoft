package com.coolCompany.sights_on_map_api.dto;

import com.coolCompany.sights_on_map_api.entity.FeedbackEntity;
import lombok.Data;

@Data
public class FeedbackResponseDTO {
    private Long id;
    private String username;
    private String text;
    private int estimation;
    private Long sightId;
    private String sightName;
    public static FeedbackResponseDTO fromEntity(FeedbackEntity entity) {
        FeedbackResponseDTO dto = new FeedbackResponseDTO();
        dto.setId(entity.getId());
        dto.setUsername(entity.getUsername());
        dto.setText(entity.getText());
        dto.setEstimation(entity.getEstimation());
        dto.setSightId(entity.getSight().getId());
        dto.setSightName(entity.getSight().getName());
        return dto;
    }
}