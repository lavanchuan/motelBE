package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.MessageDTO;
import com.motel.motel.models.e.MessageStatus;
import com.motel.motel.models.entities.MessageDAO;
import com.motel.motel.services.DateTimeFormatService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MessageMapper implements BaseMapper<MessageDAO, MessageDTO, DbContext>{
    @Override
    public MessageDTO toDTO(MessageDAO messageDAO) {

        MessageDTO dto = new MessageDTO();
        dto.setId(messageDAO.getId());
        dto.setCreateAt(messageDAO.getCreateAt());
        dto.setMessage(messageDAO.getMessage());
        dto.setStatus(messageDAO.getStatus());
        if(messageDAO.getSender()!=null) dto.setSenderId(messageDAO.getSender().getId());
        if(messageDAO.getReceiver()!=null) dto.setReceiverId(messageDAO.getReceiver().getId());

        return dto;
    }

    @Override
    public MessageDAO toDAO(MessageDTO messageDTO, DbContext dbContext) {

        MessageDAO dao = new MessageDAO();

        if(dbContext.messageRepository.existsById(messageDTO.getId())){

            dao = dbContext.messageRepository.findById(messageDTO.getId()).orElseThrow();

            if(messageDTO.getCreateAt() != null && !messageDTO.getCreateAt().isEmpty())
                dao.setCreateAt(DateTimeFormatService.toLocalDateTime(messageDTO.getCreateAt()));

            if(messageDTO.getMessage()!=null && !messageDTO.getMessage().isEmpty())
                dao.setMessage(messageDTO.getMessage());

            if(messageDTO.getStatus() != null) dao.setStatus(messageDTO.getStatus());

        } else {
            if(messageDTO.getCreateAt() != null && !messageDTO.getCreateAt().isEmpty())
                dao.setCreateAt(DateTimeFormatService.toLocalDateTime(messageDTO.getCreateAt()));
            else dao.setCreateAt(LocalDateTime.now());

            dao.setMessage(messageDTO.getMessage());
            if(messageDTO.getStatus() != null) dao.setStatus(messageDTO.getStatus());
            else dao.setStatus(MessageStatus.SANDED);
        }

        if(dbContext.accountRepository.existsById(messageDTO.getSenderId()))
            dao.setSender(dbContext.accountRepository.findById(messageDTO.getSenderId()).orElseThrow());
        if(dbContext.accountRepository.existsById(messageDTO.getReceiverId()))
            dao.setReceiver(dbContext.accountRepository.findById(messageDTO.getReceiverId()).orElseThrow());

        return dao;
    }
}
