package com.motel.motel.services;

import java.util.List;

public interface ICRUDService <DTO, ID, RESPONSE>{
    RESPONSE add(DTO dto);
    RESPONSE update(DTO dto);
    RESPONSE delete(ID id);
    List<DTO> findAll();
    DTO findById(ID id);
}
