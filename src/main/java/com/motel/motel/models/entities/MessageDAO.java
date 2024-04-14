package com.motel.motel.models.entities;

import com.motel.motel.models.e.MessageStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "message")
@Data
public class MessageDAO extends BaseEntity{

    private LocalDateTime createAt;

    @Column(length = 500)
    private String message;

    @Enumerated(EnumType.ORDINAL)
    private MessageStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "senderId")
    private AccountDAO sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver")
    private AccountDAO receiver;

}
