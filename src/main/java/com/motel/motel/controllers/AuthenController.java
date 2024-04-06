package com.motel.motel.controllers;

import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.request.AccountRegisterRequest;
import com.motel.motel.models.request.LoginRequest;
import com.motel.motel.models.response.AccountResponse;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.services.AuthenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/authen")
public class AuthenController {

    @Autowired
    AuthenService authenService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AccountRegisterRequest request){
        BaseResponse<AccountDTO> response = authenService.register(request);

        switch (response.getStatus()){
            case BaseResponse.SUCCESS:
                return ResponseEntity.ok(response);
            case BaseResponse.CONFLICT:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request){
        BaseResponse<AccountDTO> response = authenService.authentication(request);

        switch (response.getStatus()){
            case BaseResponse.SUCCESS:
                return ResponseEntity.ok(response);
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(response);
        }
    }
}
