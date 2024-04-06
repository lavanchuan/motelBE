package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.entities.AccountDAO;
import com.motel.motel.services.DateTimeFormatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AccountMapper implements BaseMapper<AccountDAO, AccountDTO, DbContext>{

    @Override
    public AccountDTO toDTO(AccountDAO accountDAO) {

        AccountDTO dto = new AccountDTO();

        dto.setId(accountDAO.getId());
        dto.setCreateAt(accountDAO.getCreateAt());
        dto.setName(accountDAO.getName());
        dto.setMail(accountDAO.getMail());
        dto.setPhone(accountDAO.getPhone());
        dto.setAddress(accountDAO.getAddress());
        dto.setSex(accountDAO.getSex());
        dto.setDateOfBirth(accountDAO.getDateOfBirth());
        dto.setStatus(accountDAO.getStatus());
        if(accountDAO.getRoleDAO() != null) dto.setRole(accountDAO.getRoleDAO().getName());
        if(accountDAO.getRoleDAO() != null) dto.setRoleId(accountDAO.getRoleDAO().getId());

        return dto;
    }

    @Override
    public AccountDAO toDAO(AccountDTO dto, DbContext db) {
        AccountDAO dao = new AccountDAO();

        if(dto.getId() > 0 && db.accountRepository.existsById(dto.getId())){
            // update
            dao = db.accountRepository.findById(dto.getId()).orElseThrow();

            if(dto.getCreateAt() != null) dao.setCreateAt(DateTimeFormatService.toLocalDateTime(dto.getCreateAt()));
            if(dto.getName() != null && !dto.getName().isEmpty()) dao.setName(dto.getName());
            if(dto.getMail() != null && !dto.getMail().isEmpty()) dao.setMail(dto.getMail());
            if(dto.getPhone() != null && !dto.getPhone().isEmpty()) dao.setPhone(dto.getPhone());
            if(dto.getAddress() != null && !dto.getAddress().isEmpty()) dao.setAddress(dto.getAddress());
            if(dto.getSex() != null) dao.setSex(dto.getSex());
            if(dto.getDateOfBirth() != null) dao.setDateOfBirth(dto.getDateOfBirth());
            if(dto.getStatus() != null && !dto.getStatus().isEmpty()) dao.setStatus(dto.getStatus());

            if(dto.getRoleId() > 0 && db.roleRepository.existsById(dto.getRoleId())){
                dao.setRoleDAO(db.roleRepository.findById(dto.getRoleId()).orElseThrow());
            }
        } else {
            // add
            dao.setCreateAt(LocalDateTime.now());
            dao.setName(dto.getName());
            dao.setMail(dto.getMail());
            dao.setPhone(dto.getPhone());
            dao.setAddress(dto.getAddress());
            dao.setSex(dto.getSex());
            dao.setDateOfBirth(dto.getDateOfBirth());
            dao.setStatus(dto.getStatus());

            if(dto.getRoleId() > 0 && db.roleRepository.existsById(dto.getRoleId())){
                dao.setRoleDAO(db.roleRepository.findById(dto.getRoleId()).orElseThrow());
            } else {
                dao.setRoleDAO(db.roleRepository.findByName(dto.getRole()));
            }
        }


        return dao;
    }
}
