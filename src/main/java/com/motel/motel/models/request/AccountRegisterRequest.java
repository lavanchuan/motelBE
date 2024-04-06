package com.motel.motel.models.request;

import com.motel.motel.models.e.RoleName;
import com.motel.motel.models.e.SexName;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountRegisterRequest {
    // ACCOUNT
    private String name;
    private String mail;
    private String phone;
    private String address;
    private SexName sex;
    private LocalDate dateOfBirth;
    private RoleName role = RoleName.USER;

    // PASSWORD
    private String pass;
}
