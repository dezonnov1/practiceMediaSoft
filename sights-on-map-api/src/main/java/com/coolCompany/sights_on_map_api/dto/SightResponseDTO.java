package com.coolCompany.sights_on_map_api.dto;

import com.coolCompany.sights_on_map_api.entity.SightEntity;
import lombok.Data;

@Data
public class SightResponseDTO {
    private Long id;
    private String name;
    private String city;
    private String category;
    private double latitude;
    private double longitude;

    public static SightResponseDTO fromEntity(SightEntity entity) {
        SightResponseDTO dto = new SightResponseDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setCity(entity.getCity());
        dto.setCategory(entity.getCategory());
        dto.setLatitude(entity.getPosition().getY());
        dto.setLongitude(entity.getPosition().getX());
        return dto;
    }
}
