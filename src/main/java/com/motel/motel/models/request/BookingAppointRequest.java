package com.motel.motel.models.request;

import com.motel.motel.services.DateTimeFormatService;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingAppointRequest {
    private LocalDateTime meetTime;
    private String reason;
    private int motelRoomId;
    private int userId;

    public void setMeetTime(LocalDateTime meetTime) {
        this.meetTime = meetTime;
    }

    public void setMeetTime(String meetTime) {
        this.meetTime = DateTimeFormatService.toLocalDateTime(meetTime);
    }
}
