package com.vr.tourism.mapper;


import com.vr.tourism.dto.HotspotDTO;
import com.vr.tourism.entity.Hotspot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotspotMapper extends BaseMapper<Hotspot, HotspotDTO> {

    @Mapping(target = "sceneId", source = "scene.id")
    @Override
    HotspotDTO toDTO(Hotspot entity);

    @Mapping(target = "scene", ignore = true)
    @Override
    Hotspot toEntity(HotspotDTO dto);
}