package com.motel.motel.services;

import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.MailDTO;
import com.motel.motel.models.dtos.PasswordDTO;
import com.motel.motel.models.entities.AccountDAO;
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

    @Autowired
    MailSenderService mailSenderService;

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

    //LOGIN EMAIL OR PHONE
    public AccountResponse auth(LoginRequest request){
        if(isPhoneNumber(request.getMail())){// log with phone
            if(!adminService.accountService.exsistsByPhone(request.getMail())) return new AccountResponse(BaseResponse.ERROR);
            AccountDTO dto = adminService.accountService.findByPhone(request.getMail());
            if(dto == null) return new AccountResponse(BaseResponse.ERROR);
            return new AccountResponse(dto);
        }

        return authentication(request);
    }

    private boolean isPhoneNumber(String phone) {
        for(char c : phone.toCharArray()) if(c < '0' || c > '9') return false;
        return true;
    }

    private boolean isValidAccount(LoginRequest request) {
        return adminService.accountService.isValidAccount(request);
    }

    public MailSenderService.MailSenderResponse forgotPassword(String email){
        if(!adminService.accountService.existsByMail(email)) return new MailSenderService.MailSenderResponse(BaseResponse.ERROR);

        String password = adminService.accountService.getPassword(email);

        MailDTO mailDTO = new MailDTO();
        mailDTO.setTo(email);
        mailDTO.setSubject(String.format("Password: '%s'", password));
        mailDTO.setContent(String.format("Password: '%s' for email: '%s'", password, email));

        mailSenderService.sendMail(mailDTO);

        return new MailSenderService.MailSenderResponse("The password has been sent to your email");
    }
}
