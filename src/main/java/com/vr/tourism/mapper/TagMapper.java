package com.vr.tourism.mapper;

import com.vr.tourism.dto.TagDTO;
import com.vr.tourism.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TagMapper extends BaseMapper<Tag, TagDTO> {

    @Mapping(target = "destinationId", source = "destination.id")
    @Override
    TagDTO toDTO(Tag entity);

    @Mapping(target = "destination", ignore = true)
    @Override
    Tag toEntity(TagDTO dto);
}
