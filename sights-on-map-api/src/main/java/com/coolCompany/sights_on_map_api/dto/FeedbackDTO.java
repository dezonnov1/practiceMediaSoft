package com.coolCompany.sights_on_map_api.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class FeedbackDTO {
    private Long sightId;
    private String sightName;
    @NotBlank(message = "username is required")
    private String username;
    private String text;
    @Min(value = 1)
    @Max(value = 5)
    private int estimation;
}
