package com.motel.motel.models.entities;

import com.motel.motel.models.e.MotelStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "motel")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotelDAO extends BaseEntity{
    private LocalDate createAt;
    @Column(length = 100)
    private String name;
    @Column(length = 500)
    private String address;
    @Column(length = 100)
    private String electricPrice;
    @Column(length = 100)
    private String waterPrice;
    @Enumerated(EnumType.ORDINAL)
    private MotelStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ownerId")
    private AccountDAO accountDAO;
}
