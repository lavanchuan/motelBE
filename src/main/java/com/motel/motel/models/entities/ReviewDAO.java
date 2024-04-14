package com.motel.motel.models.entities;

import com.motel.motel.models.e.ReviewStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Data
public class ReviewDAO extends BaseEntity{

    private LocalDateTime creatAt;

    @Column(length = 500)
    private String comment;

    private int rate;

    @Enumerated(EnumType.ORDINAL)
    private ReviewStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookRoomId")
    private BookRoomDAO bookRoomDAO;
}
