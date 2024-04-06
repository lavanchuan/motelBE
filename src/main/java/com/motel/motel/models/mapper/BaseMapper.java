package com.motel.motel.models.mapper;

public interface BaseMapper <DAO, DTO, CONTEXT>{
    DTO toDTO(DAO dao);

    DAO toDAO(DTO dto, CONTEXT context);
}
