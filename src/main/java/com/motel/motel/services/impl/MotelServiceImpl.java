package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.MotelDTO;
import com.motel.motel.models.entities.MotelDAO;
import com.motel.motel.models.mapper.MotelMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.OtherResponse;
import com.motel.motel.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotelServiceImpl implements ICRUDService<MotelDTO, Integer, OtherResponse<MotelDTO>> {
    @Autowired
    DbContext dbContext;

    @Autowired
    MotelMapper motelMapper;

    @Override
    public OtherResponse<MotelDTO> add(MotelDTO motelDTO) {
        if (dbContext.motelRepository.existsById(motelDTO.getId())) return new OtherResponse<>(BaseResponse.ERROR);
        return new OtherResponse<>(motelMapper.toDTO(dbContext.motelRepository.save(
                motelMapper.toDAO(motelDTO, dbContext)
        )));
    }

    @Override
    public OtherResponse<MotelDTO> update(MotelDTO motelDTO) {
        if (!dbContext.motelRepository.existsById(motelDTO.getId())) return new OtherResponse<>(BaseResponse.ERROR);
        return new OtherResponse<>(motelMapper.toDTO(dbContext.motelRepository.save(motelMapper.toDAO(motelDTO, dbContext))));
    }

    @Override
    public OtherResponse<MotelDTO> delete(Integer integer) {
        return null;
    }

    @Override
    public List<MotelDTO> findAll() {
        return dbContext.motelRepository.findAll().stream().map(motelMapper::toDTO)
                .toList();
    }

    @Override
    public MotelDTO findById(Integer id) {
        return motelMapper.toDTO(dbContext.motelRepository.findById(id).orElseThrow());
    }

    public boolean existsById(int motelId) {
        return dbContext.motelRepository.existsById(motelId);
    }
}
