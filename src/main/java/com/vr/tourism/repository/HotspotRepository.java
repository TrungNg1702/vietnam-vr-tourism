package com.vr.tourism.repository;

import com.vr.tourism.entity.Hotspot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotspotRepository extends JpaRepository<Hotspot, Long> {
    List<Hotspot> findBySceneId(Long sceneId);
}
