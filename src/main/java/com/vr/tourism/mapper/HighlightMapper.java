package com.vr.tourism.mapper;

import com.vr.tourism.dto.HighlightDTO;
import com.vr.tourism.entity.Highlight;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HighlightMapper extends BaseMapper<Highlight, HighlightDTO> {

    @Mapping(target = "destinationId", source = "destination.id")
    @Override
    HighlightDTO toDTO(Highlight entity);

    @Mapping(target = "destination", ignore = true)
    @Override
    Highlight toEntity(HighlightDTO dto);
}
