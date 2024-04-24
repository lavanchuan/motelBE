package com.motel.motel.models.dtos;

import com.motel.motel.models.e.AccountStatus;
import com.motel.motel.models.e.RoleName;
import com.motel.motel.models.e.SexName;
import com.motel.motel.services.DateTimeFormatService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {


    private int id;
    private LocalDateTime createAt;
    private String name;
    private String mail;
    private String phone;
    private String address;
    private SexName sex;
    private LocalDate dateOfBirth;
    private AccountStatus status;
    private int roleId;
    private RoleName role;


    // DateTime Format
    public void setCreateAt(String createAt) {
        this.createAt = DateTimeFormatService.toLocalDateTime(createAt);
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public String getCreateAt() {
        return DateTimeFormatService.toDateTimeString(this.createAt);
    }
}
