package com.coolCompany.sights_on_map_api.dto;

import lombok.Data;

@Data
public class SightDTO {
    private String name;
    private String city;
    private double latitude;
    private double longitude;
    private String category;
}