package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.BookRoomDTO;
import com.motel.motel.models.entities.BookRoomDAO;
import com.motel.motel.services.DateTimeFormatService;
import org.springframework.stereotype.Component;

@Component
public class BookRoomMapper implements BaseMapper<BookRoomDAO, BookRoomDTO, DbContext>{
    @Override
    public BookRoomDTO toDTO(BookRoomDAO bookRoomDAO) {

        BookRoomDTO dto = new BookRoomDTO();

        dto.setId(bookRoomDAO.getId());
        dto.setCreateAt(bookRoomDAO.getCreateAt());
        dto.setStartTime(bookRoomDAO.getStartTime());
        dto.setEndTime(bookRoomDAO.getEndTime());
        dto.setPrice(bookRoomDAO.getPrice());
        dto.setStatus(bookRoomDAO.getStatus());
        dto.setReason(bookRoomDAO.getReason());
        if(bookRoomDAO.getMotelRoomDAO() != null) dto.setMotelRoomId(bookRoomDAO.getMotelRoomDAO().getId());
        if(bookRoomDAO.getAccountDAO() != null) dto.setUserId(bookRoomDAO.getAccountDAO().getId());

        return dto;
    }

    @Override
    public BookRoomDAO toDAO(BookRoomDTO bookRoomDTO, DbContext dbContext) {

        BookRoomDAO dao = new BookRoomDAO();

        if(dbContext.bookRoomRepository.existsById(bookRoomDTO.getId())){
            dao = dbContext.bookRoomRepository.findById(bookRoomDTO.getId()).orElseThrow();

            if(bookRoomDTO.getCreateAt() != null && !bookRoomDTO.getCreateAt().isEmpty()) dao.setCreateAt(DateTimeFormatService.toLocalDateTime(bookRoomDTO.getCreateAt()));
            if(bookRoomDTO.getStartTime() != null && !bookRoomDTO.getStartTime().isEmpty()) dao.setStartTime(DateTimeFormatService.toLocalDateTime(bookRoomDTO.getStartTime()));
            if(bookRoomDTO.getEndTime() != null && !bookRoomDTO.getEndTime().isEmpty()) dao.setEndTime(DateTimeFormatService.toLocalDateTime(bookRoomDTO.getEndTime()));

            if(bookRoomDTO.getPrice() > 0) dao.setPrice(bookRoomDTO.getPrice());

            if(bookRoomDTO.getStatus() != null) dao.setStatus(bookRoomDTO.getStatus());

            if(bookRoomDTO.getReason() != null && !bookRoomDTO.getReason().isEmpty()) dao.setReason(bookRoomDTO.getReason());
        } else {
            dao.setCreateAt(DateTimeFormatService.toLocalDateTime(bookRoomDTO.getCreateAt()));
            dao.setStartTime(DateTimeFormatService.toLocalDateTime(bookRoomDTO.getStartTime()));
            dao.setEndTime(DateTimeFormatService.toLocalDateTime(bookRoomDTO.getEndTime()));

            dao.setPrice(bookRoomDTO.getPrice());

            dao.setStatus(bookRoomDTO.getStatus());

            dao.setReason(bookRoomDTO.getReason());
        }

        if(dbContext.motelRoomRepository.existsById(bookRoomDTO.getMotelRoomId())) {
            dao.setMotelRoomDAO(dbContext.motelRoomRepository.findById(bookRoomDTO.getMotelRoomId()).orElseThrow());
        }

        if(dbContext.accountRepository.existsById(bookRoomDTO.getUserId())){
            dao.setAccountDAO(dbContext.accountRepository.findById(bookRoomDTO.getUserId()).orElseThrow());
        }

        return dao;
    }
}
