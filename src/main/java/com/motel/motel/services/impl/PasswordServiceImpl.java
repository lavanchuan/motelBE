package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.entities.PasswordDAO;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.PasswordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl{

    @Autowired
    DbContext dbContext;

    public PasswordResponse add(int accountId, String pass){
        if(!dbContext.accountRepository.existsById(accountId))
            return new PasswordResponse(BaseResponse.CONFLICT);

        PasswordDAO dao = new PasswordDAO();
        dao.setPass(pass);
        dao.setAccountDAO(dbContext.accountRepository.findById(accountId).orElseThrow());
        dbContext.passwordRepository.save(dao);
        return new PasswordResponse("Added Password Success");
    }

   
}
