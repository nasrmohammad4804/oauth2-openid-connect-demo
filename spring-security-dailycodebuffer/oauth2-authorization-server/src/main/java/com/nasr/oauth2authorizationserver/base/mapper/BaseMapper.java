package com.nasr.oauth2authorizationserver.base.mapper;

import java.util.List;

public interface BaseMapper <E,D>{

    D convertEntityToDto(E entity);
    E convertDtoToEntity(D dto);
    List<D> convertEntitiesToDtos(List<E> entities);
    List<E> convertDtosToEntities(List<D> dtos);
}
