package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.MotelRoomDTO;
import com.motel.motel.models.entities.MotelRoomDAO;
import com.motel.motel.services.DateTimeFormatService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MotelRoomMapper implements BaseMapper<MotelRoomDAO, MotelRoomDTO, DbContext>{
    @Override
    public MotelRoomDTO toDTO(MotelRoomDAO dao) {
        MotelRoomDTO dto = new MotelRoomDTO();

        dto.setId(dao.getId());
        dto.setName(dao.getName());
        dto.setCreateAt(dao.getCreateAt());
        dto.setArea(dao.getArea());
        dto.setPrice(dao.getPrice());
        dto.setSale(dao.getSale());
        dto.setFacility(dao.getFacility());
        dto.setStatus(dao.getStatus());
        if(dao.getMotelDAO() != null) dto.setMotelId(dao.getMotelDAO().getId());
        if(dao.getAccountDAO() != null) dto.setAdminId(dao.getAccountDAO().getId());

        return dto;
    }

    @Override
    public MotelRoomDAO toDAO(MotelRoomDTO dto, DbContext dbContext) {

        MotelRoomDAO dao = new MotelRoomDAO();

        if(dbContext.motelRoomRepository.existsById(dto.getId())){
            dao = dbContext.motelRoomRepository.findById(dto.getId()).orElseThrow();

            if(!isEmpty(dto.getName())) dao.setName(dto.getName());
            if(dto.getCreateAt() != null) dao.setCreateAt(DateTimeFormatService.toLocalDateTime(dto.getCreateAt()));
            if(!isEmpty(dto.getArea())) dao.setArea(dto.getArea());
            if(dto.getPrice() > 0) dao.setPrice(dto.getPrice());
            if(dto.getSale() > 0) dao.setSale(dto.getSale());
            if(!isEmpty(dto.getFacility())) dao.setFacility(dto.getFacility());
            if(dto.getStatus() != null) dao.setStatus(dto.getStatus());
        } else {
            dao.setName(dto.getName());
            if(dto.getCreateAt() != null) dao.setCreateAt(DateTimeFormatService.toLocalDateTime(dto.getCreateAt()));
            else dao.setCreateAt(LocalDateTime.now());
            dao.setArea(dto.getArea());
            dao.setPrice(dto.getPrice());
            dao.setSale(dto.getSale());
            dao.setFacility(dto.getFacility());
            dao.setStatus(dto.getStatus());
        }

        if(dbContext.motelRepository.existsById(dto.getMotelId()))
            dao.setMotelDAO(dbContext.motelRepository.findById(dto.getMotelId()).orElseThrow());
        if(dbContext.accountRepository.existsById(dto.getAdminId()))
            dao.setAccountDAO(dbContext.accountRepository.findById(dto.getAdminId()).orElseThrow());

        return dao;
    }

    private boolean isEmpty(String name) {
        return name != null && !name.isEmpty();
    }
}
