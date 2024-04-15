package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.PasswordDTO;
import com.motel.motel.models.entities.PasswordDAO;
import org.springframework.stereotype.Component;

@Component
public class PasswordMapper implements BaseMapper<PasswordDAO, PasswordDTO, DbContext> {
    @Override
    public PasswordDTO toDTO(PasswordDAO passwordDAO) {

        PasswordDTO dto = new PasswordDTO();
        dto.setId(passwordDAO.getId());
        dto.setPass(passwordDAO.getPass());
        if (passwordDAO.getAccountDAO() != null) dto.setAccountId(passwordDAO.getAccountDAO().getId());

        return dto;
    }

    @Override
    public PasswordDAO toDAO(PasswordDTO passwordDTO, DbContext dbContext) {

        PasswordDAO dao = new PasswordDAO();
        if (dbContext.passwordRepository.existsById(passwordDTO.getId())) {
            dao = dbContext.passwordRepository.findById(passwordDTO.getId()).orElseThrow();
            if (passwordDTO.getPass() != null && !passwordDTO.getPass().isEmpty()) dao.setPass(passwordDTO.getPass());
        } else {
            dao.setPass(passwordDTO.getPass());
        }

        if (dbContext.accountRepository.existsById(passwordDTO.getAccountId()))
            dao.setAccountDAO(dbContext.accountRepository.findById(passwordDTO.getAccountId()).orElseThrow());

        return dao;
    }
}
