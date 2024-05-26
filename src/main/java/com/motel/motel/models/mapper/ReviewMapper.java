package com.motel.motel.models.mapper;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.ReviewDTO;
import com.motel.motel.models.e.ReviewStatus;
import com.motel.motel.models.entities.ReviewDAO;
import com.motel.motel.services.DateTimeFormatService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ReviewMapper implements BaseMapper<ReviewDAO, ReviewDTO, DbContext>{
    @Override
    public ReviewDTO toDTO(ReviewDAO reviewDAO) {
        ReviewDTO dto = new ReviewDTO();

        dto.setId(reviewDAO.getId());
        dto.setCreateAt(reviewDAO.getCreatAt());
        dto.setComment(reviewDAO.getComment());
        dto.setRate(reviewDAO.getRate());
        dto.setStatus(reviewDAO.getStatus());
        if(reviewDAO.getBookRoomDAO() != null) dto.setBookRoomId(reviewDAO.getBookRoomDAO().getId());

        return dto;
    }

    @Override
    public ReviewDAO toDAO(ReviewDTO reviewDTO, DbContext dbContext) {

        ReviewDAO dao = new ReviewDAO();
        if(dbContext.reviewRepository.existsById(reviewDTO.getId())){
            dao = dbContext.reviewRepository.findById(reviewDTO.getId()).orElseThrow();

            if(reviewDTO.getCreateAt() != null && !reviewDTO.getCreateAt().isEmpty()) dao.setCreatAt(DateTimeFormatService.toLocalDateTime(reviewDTO.getCreateAt()));
            if(reviewDTO.getComment() != null && !reviewDTO.getComment().isEmpty()) dao.setComment(reviewDTO.getComment());
            if(reviewDTO.getRate() > 0) dao.setRate(reviewDTO.getRate());
            if(reviewDTO.getStatus() != null) dao.setStatus(reviewDTO.getStatus());
        } else {
            if(reviewDTO.getCreateAt() == null || reviewDTO.getCreateAt().isEmpty())
                dao.setCreatAt(LocalDateTime.now());
            else dao.setCreatAt(DateTimeFormatService.toLocalDateTime(reviewDTO.getCreateAt()));
            dao.setComment(reviewDTO.getComment());
            dao.setRate(reviewDTO.getRate());
            dao.setStatus(ReviewStatus.POSTED);
        }

        if(dbContext.bookRoomRepository.existsById(reviewDTO.getBookRoomId()))
            dao.setBookRoomDAO(dbContext.bookRoomRepository.findById(reviewDTO.getBookRoomId()).orElseThrow());

        return dao;
    }
}
