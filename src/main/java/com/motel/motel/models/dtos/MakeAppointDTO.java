package com.motel.motel.models.dtos;

import com.motel.motel.models.e.MakeAppointStatus;
import com.motel.motel.services.DateTimeFormatService;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MakeAppointDTO {
    private int id;
    private LocalDateTime createAt;
    private LocalDateTime meetTime;
    private String reason;
    private MakeAppointStatus status;
    private int motelRoomId;
    private int userId;

    // DATE TIME FORMAT

//    public void setCreateAt(LocalDateTime createAt) {
//        this.createAt = createAt;
//    }
    public void setCreateAt(String createAt) {
        this.createAt = DateTimeFormatService.toLocalDateTime(createAt);
    }
    public String getCreateAt() {
        return DateTimeFormatService.toDateTimeString(createAt);
    }

//    public void setMeetTime(LocalDateTime meetTime) {
//        this.meetTime = meetTime;
//    }
    public void setMeetTime(String meetTime) {
        this.meetTime = DateTimeFormatService.toLocalDateTime(meetTime);
    }

    public String getMeetTime() {
        return DateTimeFormatService.toDateTimeString(meetTime);
    }
}
