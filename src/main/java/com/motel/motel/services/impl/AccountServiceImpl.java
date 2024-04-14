package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.e.RoleName;
import com.motel.motel.models.entities.AccountDAO;
import com.motel.motel.models.mapper.AccountMapper;
import com.motel.motel.models.request.LoginRequest;
import com.motel.motel.models.response.AccountResponse;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        if(!dbContext.accountRepository.existsById(accountDTO.getId())) return new AccountResponse(BaseResponse.ERROR);
        AccountDAO dao = dbContext.accountRepository.findById(accountDTO.getId()).orElseThrow();
        if(!dao.getPhone().equals(accountDTO.getPhone())){
            if(dbContext.accountRepository.existsByPhone(accountDTO.getPhone()))
                return new AccountResponse(BaseResponse.ERROR);
        }

        AccountDTO dto = accountMapper.toDTO(dbContext.accountRepository.save(accountMapper.toDAO(accountDTO, dbContext)));

        return new AccountResponse(dto);
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

    public boolean exsistsByPhone(String phone){return dbContext.accountRepository.existsByPhone(phone);}

    public AccountDTO findByPhone(String phone) {
        AccountDAO accountDAO = dbContext.accountRepository.findByPhone(phone);
        return accountMapper.toDTO(accountDAO);
    }

    public AccountResponse registerOwner(int accountId) {
        if(!dbContext.accountRepository.existsById(accountId)) return new AccountResponse(BaseResponse.ERROR);
        AccountDAO dao = dbContext.accountRepository.findById(accountId).orElseThrow();
        if(dao.getRoleDAO().getName() != RoleName.USER) return new AccountResponse(BaseResponse.ERROR);//TODO CHECK
        if(!isValidRequestOwnerInfo(dao)) return new AccountResponse(BaseResponse.ERROR);
        dao.setRoleDAO(dbContext.roleRepository.findByName(RoleName.OWNER));
        dao = dbContext.accountRepository.save(dao);
        return new AccountResponse(accountMapper.toDTO(dao));
    }

    private boolean isValidRequestOwnerInfo(AccountDAO dao) {
        return dao.getName() != null && !dao.getName().isEmpty() &&
                dao.getMail() != null && !dao.getMail().isEmpty() &&
                dao.getAddress() != null && !dao.getAddress().isEmpty() &&
                dao.getPhone() != null && !dao.getPhone().isEmpty() &&
                dao.getCreateAt() != null &&
                dao.getRoleDAO() != null &&
                dao.getSex() != null &&
                dao.getDateOfBirth() != null;

    }

    public boolean existsByMail(String email) {
        return dbContext.accountRepository.existsByMail(email);
    }

    public String getPassword(String email) {
        return dbContext.passwordRepository.getPassword(email);
    }

    // LIST ADMIN
    public List<AccountDTO> findAllAdmin(){
        return dbContext.accountRepository.findAllByADMIN().stream().map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }
}
