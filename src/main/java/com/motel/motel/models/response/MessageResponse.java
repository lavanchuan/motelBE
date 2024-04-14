package com.motel.motel.models.response;

import com.motel.motel.models.dtos.MessageDTO;

public class MessageResponse extends BaseResponse<MessageDTO>{
    public MessageResponse(int status) {
        super(status);
    }

    public MessageResponse(MessageDTO messageDTO) {
        super(messageDTO);
    }
}
