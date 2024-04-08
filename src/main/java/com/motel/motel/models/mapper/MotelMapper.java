package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.MotelDTO;
import com.motel.motel.models.entities.MotelDAO;
import com.motel.motel.services.DateTimeFormatService;
import org.springframework.stereotype.Component;

@Component
public class MotelMapper implements BaseMapper<MotelDAO, MotelDTO, DbContext>{
    @Override
    public MotelDTO toDTO(MotelDAO motelDAO) {
        MotelDTO dto = new MotelDTO();

        dto.setId(motelDAO.getId());
        dto.setCreateAt(motelDAO.getCreateAt());
        dto.setName(motelDAO.getName());
        dto.setAddress(motelDAO.getAddress());
        dto.setElectric_price(motelDAO.getElectricPrice());
        dto.setWater_price(motelDAO.getWaterPrice());
        dto.setStatus(motelDAO.isStatus());
        if(motelDAO.getAccountDAO() != null) dto.setOwnerId(motelDAO.getId());

        return dto;
    }

    @Override
    public MotelDAO toDAO(MotelDTO motelDTO, DbContext dbContext) {
        return null;
    }
}
