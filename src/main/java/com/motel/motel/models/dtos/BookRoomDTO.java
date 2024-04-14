package com.motel.motel.models.dtos;

import com.motel.motel.models.e.BookRoomStatus;
import com.motel.motel.services.DateTimeFormatService;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookRoomDTO {
    private int id;
    private LocalDateTime createAt;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double price;
    private BookRoomStatus status;
    private String reason;
    private int motelRoomId;
    private int userId;

    // LOCALDATETIME

    public void setCreateAt(String createAt) {
        this.createAt = DateTimeFormatService.toLocalDateTime(createAt);
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreateAt() {
        return DateTimeFormatService.toDateTimeString(createAt);
    }

    public void setStartTime(String startTime) {
        this.startTime = DateTimeFormatService.toLocalDateTime(startTime);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public String getStartTime() {
        return DateTimeFormatService.toDateTimeString(startTime);
    }

    public void setEndTime(String endTime) {
        this.endTime = DateTimeFormatService.toLocalDateTime(endTime);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getEndTime() {
        return DateTimeFormatService.toDateTimeString(endTime);
    }
}
