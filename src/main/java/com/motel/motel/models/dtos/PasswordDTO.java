package com.motel.motel.models.dtos;

import lombok.Data;

@Data
public class PasswordDTO {
    private int id;
    private String pass;
    private int accountId;
}
