package com.vr.tourism.mapper;

public interface BaseMapper<E, D> {
    D toDTO(E entity);
    E toEntity(D dto);
}
