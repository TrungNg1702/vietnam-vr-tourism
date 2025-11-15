package com.vr.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class SceneDTO {
    private String id;
    private String type;
    private String panoUrl;
    private String title;
    private String description;

    private Long destinationId;
    private List<HotspotDTO> hotspots;
}
