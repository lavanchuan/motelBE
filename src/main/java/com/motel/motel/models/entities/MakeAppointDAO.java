package com.motel.motel.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "makeAppoint")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MakeAppointDAO extends BaseEntity{
    private LocalDateTime createAt;
    private LocalDateTime meetTime;
    @Column(length = 500)
    private String reason;
    @Column(length = 100)
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motelRoomId")
    private MotelRoomDAO motelRoomDAO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private AccountDAO accountDAO;
}
