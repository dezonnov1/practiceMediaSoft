package com.coolCompany.sights_on_map_api.dto;


import lombok.Data;

@Data
public class NearbySightRequest {
    private double latitude;
    private double longitude;
    private double radiusMeters;
    private String category;
    private Double minAverageRating;
    private Integer limit;
}
