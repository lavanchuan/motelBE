package com.motel.motel.models.dtos;

import com.motel.motel.models.e.ReviewStatus;
import com.motel.motel.services.DateTimeFormatService;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private int id;
    private LocalDateTime createAt;
    private String comment;
    private int rate;
    private ReviewStatus status;
    private int bookRoomId;

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
