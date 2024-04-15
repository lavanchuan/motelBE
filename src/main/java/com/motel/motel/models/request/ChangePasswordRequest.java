package com.motel.motel.models.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private int userId;
    private String oldPass;
    private String newPass;
}
