package com.motel.motel.models.entities;

import com.motel.motel.models.e.BookRoomStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookRoom")
@Data
public class BookRoomDAO extends BaseEntity{

    private LocalDateTime createAt;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double price;

    @Enumerated(EnumType.STRING)
    private BookRoomStatus status;

    @Column(length = 500)
    private String reason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motelRoomId")
    private MotelRoomDAO motelRoomDAO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private AccountDAO accountDAO;
}
