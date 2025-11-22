package com.vr.tourism.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotspotDTO {
    private Long id;
    private String type;
    private Double yaw;
    private Double pitch;
    private String label;
    private Long target;

    private Long sceneId;
}
