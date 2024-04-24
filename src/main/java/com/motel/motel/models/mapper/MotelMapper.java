package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.MotelDTO;
import com.motel.motel.models.e.MotelStatus;
import com.motel.motel.models.entities.MotelDAO;
import com.motel.motel.services.DateTimeFormatService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MotelMapper implements BaseMapper<MotelDAO, MotelDTO, DbContext> {
    @Override
    public MotelDTO toDTO(MotelDAO motelDAO) {
        MotelDTO dto = new MotelDTO();

        dto.setId(motelDAO.getId());
        dto.setCreateAt(motelDAO.getCreateAt());
        dto.setName(motelDAO.getName());
        dto.setAddress(motelDAO.getAddress());
        dto.setElectric_price(motelDAO.getElectricPrice());
        dto.setWater_price(motelDAO.getWaterPrice());
        dto.setStatus(motelDAO.getStatus());
        if (motelDAO.getAccountDAO() != null) dto.setOwnerId(motelDAO.getAccountDAO().getId());

        return dto;
    }

    @Override
    public MotelDAO toDAO(MotelDTO motelDTO, DbContext dbContext) {

        MotelDAO dao = new MotelDAO();

        if (dbContext.motelRepository.existsById(motelDTO.getId())) {
            dao = dbContext.motelRepository.findById(motelDTO.getId()).orElseThrow();

            if (motelDTO.getCreateAt() != null) dao.setCreateAt(motelDTO.getCreateAt());
            if (motelDTO.getName() != null && !motelDTO.getName().isEmpty()) dao.setName(motelDTO.getName());
            if (motelDTO.getAddress() != null && !motelDTO.getAddress().isEmpty())
                dao.setAddress(motelDTO.getAddress());
            if (motelDTO.getElectric_price() != null && !motelDTO.getElectric_price().isEmpty())
                dao.setElectricPrice(motelDTO.getElectric_price());
            if (motelDTO.getWater_price() != null && !motelDTO.getWater_price().isEmpty())
                dao.setWaterPrice(motelDTO.getWater_price());
            if (motelDTO.getStatus() != null) dao.setStatus(motelDTO.getStatus());
        } else {
            if (motelDTO.getCreateAt() != null) dao.setCreateAt(motelDTO.getCreateAt());
            else dao.setCreateAt(LocalDate.now());
            dao.setName(motelDTO.getName());
            dao.setAddress(motelDTO.getAddress());
            dao.setElectricPrice(motelDTO.getElectric_price());
            dao.setWaterPrice(motelDTO.getWater_price());
            if(motelDTO.getStatus() != null) dao.setStatus(motelDTO.getStatus());
            else dao.setStatus(MotelStatus.PROCESSING_CREATE);
        }

        if (dbContext.accountRepository.existsById(motelDTO.getOwnerId()))
            dao.setAccountDAO(dbContext.accountRepository.findById(motelDTO.getOwnerId()).orElseThrow());

        return dao;
    }
}
