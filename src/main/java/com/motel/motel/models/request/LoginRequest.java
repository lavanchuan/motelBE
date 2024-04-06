package com.motel.motel.models.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String mail;
    @NotNull
    private String password;
}
