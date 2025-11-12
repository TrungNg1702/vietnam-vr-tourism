package com.vr.tourism.mapper;

import com.vr.tourism.dto.UserDTO;
import com.vr.tourism.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDTO> {

    @Override
    UserDTO toDTO(User entity);

    @Mapping(target = "password", ignore = true) // Bỏ qua trường password khi mapping
    @Override
    User toEntity(UserDTO dto);
}
