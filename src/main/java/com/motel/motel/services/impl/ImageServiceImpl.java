package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.ImageDTO;
import com.motel.motel.models.mapper.ImageMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.OtherResponse;
import com.motel.motel.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageServiceImpl implements ICRUDService<ImageDTO, Integer, OtherResponse<List<ImageDTO>>> {
    @Autowired
    DbContext dbContext;

    @Autowired
    ImageMapper imageMapper;

    @Override
    public OtherResponse<List<ImageDTO>> add(ImageDTO dto) {
        if(dbContext.imageRepository.existsById(dto.getId())) return new OtherResponse<>(BaseResponse.ERROR);

        dbContext.imageRepository.save(imageMapper.toDAO(dto, dbContext));

        return new OtherResponse<>(findAll());
    }

    @Override
    public OtherResponse<List<ImageDTO>> update(ImageDTO dto) {
        if(!dbContext.imageRepository.existsById(dto.getId()))
            return new OtherResponse<>(BaseResponse.ERROR);

        dbContext.imageRepository.save(imageMapper.toDAO(dto, dbContext));

        return new OtherResponse<>(findAll());
    }

    @Override
    public OtherResponse<List<ImageDTO>> delete(Integer id) {
        if(!dbContext.imageRepository.existsById(id)) return new OtherResponse<>(BaseResponse.ERROR);

        dbContext.imageRepository.deleteById(id);

        return new OtherResponse<>(findAll());
    }

    @Override
    public List<ImageDTO> findAll() {
        return dbContext.imageRepository.findAll().stream()
                .map(imageMapper::toDTO)
                .toList();
    }

    @Override
    public ImageDTO findById(Integer id) {
        if (!dbContext.imageRepository.existsById(id)) return null;
        return imageMapper.toDTO(dbContext.imageRepository.findById(id).orElseThrow());
    }

    public List<ImageDTO> findAllByRoomId(int roomId) {
        return findAll().stream()
                .filter(image -> image.getMotelRoomId() == roomId)
                .toList();
    }
}
