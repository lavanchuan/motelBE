package com.motel.motel.models.entities;

import com.motel.motel.models.e.AccountStatus;
import com.motel.motel.models.e.SexName;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDAO extends BaseEntity{

    private LocalDateTime createAt;
    @Column(length = 100)
    private String name;
    @Column(length = 200, unique = true)
    private String mail;
    @Column(length = 11, unique = true)
    private String phone;
    @Column(length = 500)
    private String address;
    @Enumerated(EnumType.ORDINAL)
    private SexName sex;
    private LocalDate dateOfBirth;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleId")
    private RoleDAO roleDAO;
}
