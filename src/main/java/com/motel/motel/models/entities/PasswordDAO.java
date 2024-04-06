package com.motel.motel.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "password")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordDAO extends BaseEntity{

    @Column(length = 20)
    private String pass;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId")
    private AccountDAO accountDAO;
}
