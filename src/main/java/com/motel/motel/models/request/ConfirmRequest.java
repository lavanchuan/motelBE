package com.motel.motel.models.request;

import lombok.Data;

@Data
public class ConfirmRequest {
    private int id;
//    private boolean isConfirm;
    private String reason;
}
