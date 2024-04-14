package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.MessageDTO;
import com.motel.motel.models.entities.MessageDAO;
import com.motel.motel.models.mapper.MessageMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.MessageResponse;
import com.motel.motel.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements ICRUDService<MessageDTO, Integer, MessageResponse> {

    @Autowired
    DbContext dbContext;

    @Autowired
    MessageMapper messageMapper;

    @Override
    public MessageResponse add(MessageDTO messageDTO) {
        if(dbContext.messageRepository.existsById(messageDTO.getId())) return new MessageResponse(BaseResponse.ERROR);

        MessageDAO dao = dbContext.messageRepository.save(messageMapper.toDAO(messageDTO, dbContext));

        return new MessageResponse(messageMapper.toDTO(dao));
    }

    @Override
    public MessageResponse update(MessageDTO messageDTO) {
        return null;
    }

    @Override
    public MessageResponse delete(Integer integer) {
        return null;
    }

    @Override
    public List<MessageDTO> findAll() {
        return null;
    }

    @Override
    public MessageDTO findById(Integer integer) {
        return null;
    }

    public List<MessageDTO> findAllBySenderReceiver(int senderId, int receiverId){
        return dbContext.messageRepository.findAllBySenderIdReceiverId(senderId, receiverId)
                .stream().map(messageMapper::toDTO).collect(Collectors.toList());
    }
}
