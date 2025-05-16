package com.coolCompany.sights_on_map_api.dto;

import lombok.Data;

@Data
public class FeedbackDTO {
    private Long sightId;
    private String username;
    private String text;
    private int estimation;
}
