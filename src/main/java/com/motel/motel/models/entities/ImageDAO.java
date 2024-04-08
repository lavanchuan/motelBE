package com.motel.motel.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "image")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImageDAO extends BaseEntity{
    private String url;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "motelRoomId")
    private MotelRoomDAO motelRoomDAO;
}
