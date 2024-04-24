package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.MakeAppointDTO;
import com.motel.motel.models.e.MakeAppointStatus;
import com.motel.motel.models.entities.MakeAppointDAO;
import com.motel.motel.services.DateTimeFormatService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MakeAppointMapper implements BaseMapper<MakeAppointDAO, MakeAppointDTO, DbContext>{
    @Override
    public MakeAppointDTO toDTO(MakeAppointDAO dao) {

        MakeAppointDTO dto = new MakeAppointDTO();
        dto.setId(dao.getId());
        dto.setCreateAt(DateTimeFormatService.toDateTimeString(dao.getCreateAt()));
        dto.setMeetTime(DateTimeFormatService.toDateTimeString(dao.getMeetTime()));
        dto.setReason(dao.getReason());
        dto.setStatus(dao.getStatus());
        if(dao.getMotelRoomDAO() != null) dto.setMotelRoomId(dao.getMotelRoomDAO().getId());
        if(dao.getAccountDAO() != null) dto.setUserId(dao.getAccountDAO().getId());

        return dto;
    }

    @Override
    public MakeAppointDAO toDAO(MakeAppointDTO dto, DbContext dbContext) {

        MakeAppointDAO dao = new MakeAppointDAO();
        if(dbContext.makeAppointRepository.existsById(dto.getId())){
            dao = dbContext.makeAppointRepository.findById(dto.getId()).orElseThrow();

            if(dto.getCreateAt() != null) dao.setCreateAt(DateTimeFormatService.toLocalDateTime(dto.getCreateAt()));
            if(dto.getMeetTime() != null) dao.setMeetTime(DateTimeFormatService.toLocalDateTime(dto.getMeetTime()));
            if(!isEmptyString(dto.getReason())) dao.setReason(dto.getReason());
            if(dto.getStatus() != null) dao.setStatus(dto.getStatus());
            if(dto.getReason() != null && !dto.getReason().isEmpty()) dao.setReason(dto.getReason());
        } else {
            if(dto.getCreateAt() != null && !dto.getCreateAt().isEmpty()) {
                // TODO
                System.out.println("LENGTH CREATE AT :" + dto.getCreateAt().length());
                dao.setCreateAt(DateTimeFormatService.toLocalDateTime(dto.getCreateAt()));
            } else dao.setCreateAt(LocalDateTime.now());

            dao.setMeetTime(DateTimeFormatService.toLocalDateTime(dto.getMeetTime()));

            dao.setReason(dto.getReason());
            if(dto.getStatus() != null) dao.setStatus(dto.getStatus());
            else dao.setStatus(MakeAppointStatus.PROCESSING_CREATE);
        }

        if(dbContext.motelRoomRepository.existsById(dto.getMotelRoomId()))
            dao.setMotelRoomDAO(dbContext.motelRoomRepository.findById(dto.getMotelRoomId()).orElseThrow());

        if(dbContext.accountRepository.existsById(dto.getUserId()))
            dao.setAccountDAO(dbContext.accountRepository.findById(dto.getUserId()).orElseThrow());

        return dao;
    }

    private boolean isEmptyString(String str) {
        return str != null && !str.isEmpty();
    }
}
