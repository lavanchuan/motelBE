package com.motel.motel.models.entities;

import com.motel.motel.models.e.NotificationStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Data
public class NotificationDAO extends BaseEntity{
    private LocalDateTime createAt;

    @Column(length = 500)
    private String message;

    @Column(length = 500)
    private String navigate;

    @Enumerated(EnumType.ORDINAL)
    private NotificationStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId")
    private AccountDAO sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reiverId")
    private AccountDAO receiver;
}
