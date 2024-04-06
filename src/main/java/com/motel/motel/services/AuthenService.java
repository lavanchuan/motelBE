package com.motel.motel.services;

import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.PasswordDTO;
import com.motel.motel.models.request.AccountRegisterRequest;
import com.motel.motel.models.request.LoginRequest;
import com.motel.motel.models.response.AccountResponse;
import com.motel.motel.models.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenService {

    @Autowired
    AdminService adminService;

    public AccountResponse register(AccountRegisterRequest request){

        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setName(request.getName());
        accountDTO.setMail(request.getMail());
        accountDTO.setPhone(request.getPhone());
        accountDTO.setAddress(request.getAddress());
        accountDTO.setSex(request.getSex());
        accountDTO.setDateOfBirth(request.getDateOfBirth());
        accountDTO.setRole(request.getRole());

        AccountResponse response = adminService.accountService.add(accountDTO);

        if(response.getStatus() != BaseResponse.SUCCESS) return response;

        PasswordDTO passwordDTO = new PasswordDTO();
        passwordDTO.setAccountId(response.getData().getId());
        passwordDTO.setPass(request.getPass());

        adminService.passwordService.add(response.getData().getId(), request.getPass());

        return response;
    }

    public AccountResponse authentication(LoginRequest request) {
        if(!isValidAccount(request)) return new AccountResponse(BaseResponse.ERROR);

        AccountDTO data = adminService.accountService.findByMail(request.getMail());
        return new AccountResponse(data);
    }

    private boolean isValidAccount(LoginRequest request) {
        return adminService.accountService.isValidAccount(request);
    }
}
