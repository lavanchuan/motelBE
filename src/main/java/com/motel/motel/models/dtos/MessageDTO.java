package com.motel.motel.models.dtos;

import com.motel.motel.models.e.MessageStatus;
import com.motel.motel.services.DateTimeFormatService;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private int id;
    private LocalDateTime createAt;
    private String message;
    private MessageStatus status;
    private int senderId;
    private int receiverId;

    //
    public void setCreateAt(String createAt) {
        this.createAt = DateTimeFormatService.toLocalDateTime(createAt);
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreateAt() {
        return DateTimeFormatService.toDateTimeString(createAt);
    }
}
