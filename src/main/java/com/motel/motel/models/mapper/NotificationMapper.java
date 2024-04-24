package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.NotificationDTO;
import com.motel.motel.models.e.NotificationStatus;
import com.motel.motel.models.entities.NotificationDAO;
import com.motel.motel.services.DateTimeFormatService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotificationMapper implements BaseMapper<NotificationDAO, NotificationDTO, DbContext> {
    @Override
    public NotificationDTO toDTO(NotificationDAO dao) {
        NotificationDTO dto = new NotificationDTO();

        dto.setId(dao.getId());
        dto.setCreateAt(dao.getCreateAt());
        dto.setMessage(dao.getMessage());
        dto.setNavigate(dao.getNavigate());
        dto.setStatus(dao.getStatus());
        if (dao.getSender() != null) dto.setSenderId(dao.getSender().getId());
        if (dao.getReceiver() != null) dto.setReceiverId(dao.getReceiver().getId());

        return dto;
    }

    @Override
    public NotificationDAO toDAO(NotificationDTO dto, DbContext dbContext) {

        NotificationDAO dao = new NotificationDAO();

        if (dbContext.notificationRepository.existsById(dto.getId())) {
            if (dto.getCreateAt() != null && !dto.getCreateAt().isEmpty())
                dao.setCreateAt(DateTimeFormatService.toLocalDateTime(dto.getCreateAt()));
            if (dto.getMessage() != null && !dto.getMessage().isEmpty()) dao.setMessage(dto.getMessage());
            if (dto.getNavigate() != null && !dto.getNavigate().isEmpty()) dao.setNavigate(dto.getNavigate());
            if (dto.getStatus() != null) dao.setStatus(dto.getStatus());
        } else {
            if (dto.getCreateAt() != null && !dto.getCreateAt().isEmpty())
                dao.setCreateAt(DateTimeFormatService.toLocalDateTime(dto.getCreateAt()));
            else dao.setCreateAt(LocalDateTime.now());
            dao.setMessage(dto.getMessage());
            dao.setNavigate(dto.getNavigate());
            if (dto.getStatus() != null) dao.setStatus(dto.getStatus());
            else dao.setStatus(NotificationStatus.SENT);
        }

        if (dbContext.accountRepository.existsById(dto.getSenderId()))
            dao.setSender(dbContext.accountRepository.findById(dto.getSenderId()).orElseThrow());

        if (dbContext.accountRepository.existsById(dto.getReceiverId()))
            dao.setReceiver(dbContext.accountRepository.findById(dto.getReceiverId()).orElseThrow());

        return dao;
    }
}
