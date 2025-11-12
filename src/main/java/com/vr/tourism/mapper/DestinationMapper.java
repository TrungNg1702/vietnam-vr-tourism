package com.vr.tourism.mapper;

import com.vr.tourism.dto.DestinationDTO;
import com.vr.tourism.entity.Destination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {SceneMapper.class})
public interface DestinationMapper extends BaseMapper<Destination, DestinationDTO> {
    @Mapping(target = "scenes", source = "scenes")
    @Override
    DestinationDTO toDTO(Destination entity);

    @Override
    Destination toEntity(DestinationDTO dto);
}
