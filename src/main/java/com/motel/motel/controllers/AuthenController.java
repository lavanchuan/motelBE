package com.motel.motel.controllers;

import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.request.AccountRegisterRequest;
import com.motel.motel.models.request.ChangePasswordRequest;
import com.motel.motel.models.request.LoginRequest;
import com.motel.motel.models.response.AccountResponse;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.OtherResponse;
import com.motel.motel.services.AuthenService;
import com.motel.motel.services.MailSenderService;
import com.motel.motel.services.UserService;
import com.motel.motel.services.impl.AccountServiceImpl;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/authen")
public class AuthenController {

    @Autowired
    AuthenService authenService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    UserService userService;

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
        BaseResponse<AccountDTO> response = authenService.auth(request);

        switch (response.getStatus()){
            case BaseResponse.SUCCESS:
                return ResponseEntity.ok(response);
            default:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(response);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AccountDTO request){
        AccountResponse response = accountService.update(request);

        switch (response.getStatus()){
            case BaseResponse.ERROR: return ResponseEntity.badRequest().build();
            default: return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/register-owner")
    public ResponseEntity<?> registerOwner(@RequestBody AccountIdRequest request){
        AccountResponse response = userService.registerOwner(request.getAccountId());

        switch (response.getStatus()){
            case BaseResponse.ERROR: return ResponseEntity.badRequest().build();
            default: return ResponseEntity.ok(response);
        }
    }

    @GetMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam(name = "mail") String mail){
        MailSenderService.MailSenderResponse response = authenService.forgotPassword(mail);

        switch (response.getStatus()){
            case BaseResponse.ERROR: return ResponseEntity.badRequest().build();
            default: return ResponseEntity.ok(response);
        }
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request){
        OtherResponse<String> response = authenService.changePassword(request);
        switch (response.getStatus()){
            case BaseResponse.ERROR: return ResponseEntity.badRequest().build();
            default: return ResponseEntity.ok(response);
        }
    }

    // Request module
    @Data
    public static class AccountIdRequest{
        private int accountId;
    }
}
