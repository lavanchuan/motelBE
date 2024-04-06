package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.entities.AccountDAO;
import com.motel.motel.models.mapper.AccountMapper;
import com.motel.motel.models.request.LoginRequest;
import com.motel.motel.models.response.AccountResponse;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements ICRUDService<AccountDTO, Integer, AccountResponse> {

    @Autowired
    DbContext dbContext;

    @Autowired
    AccountMapper accountMapper;

    @Override
    public AccountResponse add(AccountDTO accountDTO) {
        if((accountDTO.getId() > 0 && dbContext.accountRepository.existsById(accountDTO.getId())) ||
                (accountDTO.getMail() != null && dbContext.accountRepository.existsByMail(accountDTO.getMail()))){
            return new AccountResponse(BaseResponse.CONFLICT);
        }

        AccountDAO dao = dbContext.accountRepository.save(accountMapper.toDAO(accountDTO, dbContext));

        return new AccountResponse(accountMapper.toDTO(dao));
    }

    @Override
    public AccountResponse update(AccountDTO accountDTO) {
        return null;
    }

    @Override
    public AccountResponse delete(Integer integer) {
        return null;
    }

    @Override
    public List<AccountDTO> findAll() {
        return null;
    }

    @Override
    public AccountDTO findById(Integer id) {
        if(!dbContext.accountRepository.existsById(id)) return null;

        return accountMapper.toDTO(dbContext.accountRepository.findById(id).orElseThrow());
    }

    public AccountDTO findByMail(String mail) {
        return accountMapper.toDTO(dbContext.accountRepository.findByMail(mail));
    }

    public boolean isValidAccount(LoginRequest request) {
        return countAccountValid(request) > 0;
    }

    private int countAccountValid(LoginRequest request) {
        return dbContext.accountRepository.countAccountValid(request.getMail(), request.getPassword());
    }

    public boolean existsById(int id) {
        return dbContext.accountRepository.existsById(id);
    }

    public AccountDTO findByRoomId(int roomId) {
        return accountMapper.toDTO(dbContext.accountRepository
                .findByRoomId(roomId));
    }
}
