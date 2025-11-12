package com.vr.tourism.mapper;

import com.vr.tourism.dto.SceneDTO;
import com.vr.tourism.entity.Scene;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {HotspotMapper.class})
public interface SceneMapper extends BaseMapper<Scene, SceneDTO> {

    @Mapping(target = "destinationId", source = "destination.id")
    @Mapping(target = "hotspots", source = "hotspots")
    @Override
    SceneDTO toDTO(Scene entity);

    @Mapping(target = "destination", ignore = true)
    @Override
    Scene toEntity(SceneDTO dto);
}