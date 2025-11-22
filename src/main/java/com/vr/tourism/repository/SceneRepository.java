package com.vr.tourism.repository;

import com.vr.tourism.entity.Scene;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SceneRepository extends JpaRepository<Scene, Long> {
    List<Scene> findByDestinationId(Long destinationId);
}
