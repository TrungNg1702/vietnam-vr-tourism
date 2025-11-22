package com.vr.tourism.service;

import com.vr.tourism.dto.HotspotDTO;
import com.vr.tourism.entity.Hotspot;
import com.vr.tourism.entity.Scene;
import com.vr.tourism.mapper.HotspotMapper;
import com.vr.tourism.repository.HotspotRepository;
import com.vr.tourism.repository.SceneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HotspotService {
    private final HotspotRepository repo;
    private final HotspotMapper mapper;
    private final SceneRepository sceneRepo;

    public HotspotDTO create(HotspotDTO dto) {
            Scene existingScene = sceneRepo.findById(dto.getSceneId())
                    .orElseThrow(() -> new IllegalArgumentException("Scene not found"));

            if(dto.getTarget() != null){
                sceneRepo.findById(dto.getTarget()) //dung repo cua scene de tim xem co scene id nay trong target khong
                        .orElseThrow(() -> new IllegalArgumentException("Scene not found"));
            }

            Hotspot hotspot = mapper.toEntity(dto);
            hotspot.setScene(existingScene);
            Hotspot saved = repo.save(hotspot);
            return mapper.toDTO(saved);
    }

    public HotspotDTO update(HotspotDTO dto) {
        Hotspot existingHotspot = repo.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Hotspot not found"));

        Hotspot updated = mapper.toEntity(dto);
        updated.setId(existingHotspot.getId());
        Hotspot saved = repo.save(updated);
        return mapper.toDTO(saved);
    }
}
