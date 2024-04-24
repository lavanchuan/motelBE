package com.motel.motel.models.entities;

import com.motel.motel.models.e.RoomStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "motelRoom")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MotelRoomDAO extends BaseEntity{
    @Column(length = 100)
    private String name;
    private LocalDateTime createAt;
    @Column(length = 100)
    private String area;
    private double price;
    private int sale;
    private String facility;
//    @Column(length = 100)
    @Enumerated(EnumType.STRING)
    private RoomStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motelId")
    private MotelDAO motelDAO;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adminId")
    private AccountDAO accountDAO;
}
