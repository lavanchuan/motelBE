package com.motel.motel.models.response;

import com.motel.motel.models.dtos.PasswordDTO;
import lombok.Data;

public class PasswordResponse extends BaseResponse<String>{
    public PasswordResponse(int status) {
        super(status);
    }

    public PasswordResponse(String notification) {
        super(notification);
    }
}
