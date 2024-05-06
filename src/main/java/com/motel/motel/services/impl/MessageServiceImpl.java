package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.MessageDTO;
import com.motel.motel.models.e.MessageStatus;
import com.motel.motel.models.entities.MessageDAO;
import com.motel.motel.models.mapper.MessageMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.MessageResponse;
import com.motel.motel.models.response.ObjResponse;
import com.motel.motel.models.response.OtherResponse;
import com.motel.motel.services.ICRUDService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements ICRUDService<MessageDTO, Integer, MessageResponse> {

    @Autowired
    DbContext dbContext;

    @Autowired
    MessageMapper messageMapper;

    @Autowired
    AccountServiceImpl accountService;

    @Override
    public MessageResponse add(MessageDTO messageDTO) {
        if (dbContext.messageRepository.existsById(messageDTO.getId())) return new MessageResponse(BaseResponse.ERROR);

        MessageDAO dao = dbContext.messageRepository.save(messageMapper.toDAO(messageDTO, dbContext));

        return new MessageResponse(messageMapper.toDTO(dao));
    }

    @Override
    public MessageResponse update(MessageDTO request) {
        if(!dbContext.messageRepository.existsById(request.getId())) return new MessageResponse(BaseResponse.ERROR);
        dbContext.messageRepository.save(messageMapper.toDAO(request, dbContext));
        return new MessageResponse(findById(request.getId()));
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

    public List<MessageDTO> findAllBySenderReceiver(int senderId, int receiverId) {
        return dbContext.messageRepository.findAllBySenderIdReceiverId(senderId, receiverId)
                .stream().map(messageMapper::toDTO).collect(Collectors.toList());
    }

    public OtherResponse<MessageAllOfSender> findMessageAllOfSender(int senderId) {
        List<Integer> receiverIds = dbContext.messageRepository.findAllReceiveIdBySenderId(senderId);
        if (receiverIds.isEmpty()) return new OtherResponse<>(null);

        MessageAllOfSender data = new MessageAllOfSender();
        data.setSender(accountService.findById(senderId));
        List<MessageAllOfReceiver> messageAllOfReceiverList = new ArrayList<>();

        for(int receiverId : receiverIds){
            MessageAllOfReceiver messageAllOfReceiver = new MessageAllOfReceiver();
            messageAllOfReceiver.setReceiver(accountService.findById(receiverId));
            messageAllOfReceiver.setMessageList(findAllBySenderReceiver(senderId, receiverId));
            messageAllOfReceiverList.add(messageAllOfReceiver);
        }
        data.setMessageAllOfReceiverList(messageAllOfReceiverList);

        return new OtherResponse<>(data);
    }

    public ObjResponse.BaseCount countMessageReceived(int receiverId) {
        int count = (int) dbContext.messageRepository.findAllReceived(receiverId)
                .stream().map(messageMapper::toDTO)
                .filter(message -> message.getStatus() == MessageStatus.SANDED)
                .count();

        return new ObjResponse.BaseCount(count);
    }

    // class
    @Data
    public static class MessageAllOfSender {
        private AccountDTO sender;
        private List<MessageAllOfReceiver> messageAllOfReceiverList;
    }

    @Data
    public static class MessageAllOfReceiver {
        private AccountDTO receiver;
        private List<MessageDTO> messageList;
    }
}
