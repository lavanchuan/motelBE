package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.MakeAppointDTO;
import com.motel.motel.models.entities.MakeAppointDAO;
import com.motel.motel.models.mapper.MakeAppointMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MakeAppointServiceImpl implements ICRUDService<MakeAppointDTO, Integer, BaseResponse<MakeAppointDTO>> {

    @Autowired
    DbContext dbContext;

    @Autowired
    MakeAppointMapper makeAppointMapper;

    @Override
    public BaseResponse<MakeAppointDTO> add(MakeAppointDTO makeAppointDTO) {

        if (dbContext.makeAppointRepository.existsById(makeAppointDTO.getId()))
            return new MakeAppointResponse(BaseResponse.ERROR);

        return new MakeAppointResponse(makeAppointMapper.toDTO(dbContext
                .makeAppointRepository.save(makeAppointMapper
                        .toDAO(makeAppointDTO, dbContext))));
    }

    @Override
    public BaseResponse<MakeAppointDTO> update(MakeAppointDTO makeAppointDTO) {
        if (!dbContext.makeAppointRepository.existsById(makeAppointDTO.getId()))
            return new MakeAppointResponse(BaseResponse.ERROR);

        return new MakeAppointResponse(makeAppointMapper.toDTO(dbContext
                .makeAppointRepository.save(makeAppointMapper
                        .toDAO(makeAppointDTO, dbContext))));
    }

    @Override
    public BaseResponse<MakeAppointDTO> delete(Integer integer) {
        return null;
    }

    @Override
    public List<MakeAppointDTO> findAll() {
        return dbContext.makeAppointRepository.findAll()
                .stream().map(makeAppointMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MakeAppointDTO findById(Integer id) {
        if (dbContext.makeAppointRepository.existsById(id))
            return makeAppointMapper.toDTO(dbContext.makeAppointRepository.findById(id).orElseThrow());
        return null;
    }

    public List<MakeAppointDTO> findAllByOwnerId(int ownerId) {
        return dbContext.makeAppointRepository.findAllByOwnerId(ownerId)
                .stream().map(makeAppointMapper::toDTO)
                .toList();
    }

    // CLASS
    static class MakeAppointResponse extends BaseResponse<MakeAppointDTO> {

        public MakeAppointResponse(int status) {
            super(status);
        }

        public MakeAppointResponse(MakeAppointDTO makeAppointDTO) {
            super(makeAppointDTO);
        }
    }
}
