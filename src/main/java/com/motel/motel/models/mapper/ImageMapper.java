package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.ImageDTO;
import com.motel.motel.models.entities.ImageDAO;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper implements BaseMapper<ImageDAO, ImageDTO, DbContext> {
    @Override
    public ImageDTO toDTO(ImageDAO imageDAO) {
        ImageDTO dto = new ImageDTO();
        dto.setId(imageDAO.getId());
        dto.setUrl(imageDAO.getUrl());
        if (imageDAO.getMotelRoomDAO() != null) dto.setMotelRoomId(imageDAO.getMotelRoomDAO().getId());
        return dto;
    }

    @Override
    public ImageDAO toDAO(ImageDTO dto, DbContext dbContext) {
        ImageDAO dao = new ImageDAO();

        if(dbContext.imageRepository.existsById(dto.getId())){
            dao = dbContext.imageRepository.findById(dto.getId()).orElseThrow();
            if(dto.getUrl() != null && !dto.getUrl().isEmpty()) dao.setUrl(dto.getUrl());
        } else {
            dao.setUrl(dto.getUrl());
        }

        if(dbContext.motelRoomRepository.existsById(dto.getMotelRoomId()))
            dao.setMotelRoomDAO(dbContext.motelRoomRepository
                    .findById(dto.getMotelRoomId()).orElseThrow());

        return dao;
    }
}
