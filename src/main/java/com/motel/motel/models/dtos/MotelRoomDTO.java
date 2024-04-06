package com.motel.motel.models.dtos;

import com.motel.motel.models.e.RoomStatus;
import com.motel.motel.services.DateTimeFormatService;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MotelRoomDTO {
    private int id;
    private String name;
    private LocalDateTime createAt;
    private String area;
    private double price;
    private int sale;
    private String facility;
    private RoomStatus status;
    private int motelId;
    private int adminId;

    // DateTime Format
    public void setCreateAt(String createAt) {
        this.createAt = DateTimeFormatService.toLocalDateTime(createAt);
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreateAt() {
        return DateTimeFormatService.toDateTimeString(this.createAt);
    }
}
