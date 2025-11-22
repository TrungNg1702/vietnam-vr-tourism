package com.vr.tourism.controller;

import com.vr.tourism.dto.HotspotDTO;
import com.vr.tourism.service.HotspotService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hotspots")
@AllArgsConstructor
public class HotspotController {

    private final HotspotService hotspotService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody HotspotDTO dto ){
        HotspotDTO hotspot = hotspotService.create(dto);
        return ResponseEntity.ok(hotspot);
    }
}
