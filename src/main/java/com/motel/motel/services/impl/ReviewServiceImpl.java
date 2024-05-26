package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.BookRoomDTO;
import com.motel.motel.models.dtos.ReviewDTO;
import com.motel.motel.models.e.BookRoomStatus;
import com.motel.motel.models.entities.AccountDAO;
import com.motel.motel.models.mapper.AccountMapper;
import com.motel.motel.models.mapper.BookRoomMapper;
import com.motel.motel.models.mapper.ReviewMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.ObjResponse;
import com.motel.motel.models.response.ReviewResponse;
import com.motel.motel.services.ICRUDService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ICRUDService<ReviewDTO, Integer, ReviewResponse> {

    @Autowired
    DbContext dbContext;

    @Autowired
    ReviewMapper reviewMapper;

    //Other mapper
    @Autowired
    BookRoomMapper bookRoomMapper;

    @Override
    public ReviewResponse add(ReviewDTO reviewDTO) {
        System.out.println("//TODO");

        if(dbContext.reviewRepository.existsById(reviewDTO.getId())) return new ReviewResponse(BaseResponse.ERROR);
        dbContext.reviewRepository.save(reviewMapper.toDAO(reviewDTO, dbContext));

        List<ReviewDTO> result = findAll().stream().filter(review -> review.getBookRoomId() == reviewDTO.getBookRoomId())
                .toList();

        return new ReviewResponse(result);
    }

    @Override
    public ReviewResponse update(ReviewDTO reviewDTO) {
        return null;
    }

    @Override
    public ReviewResponse delete(Integer integer) {
        return null;
    }

    @Override
    public List<ReviewDTO> findAll() {
        return dbContext.reviewRepository.findAll().stream().map(reviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ReviewDTO findById(Integer integer) {
        return null;
    }

    public ObjResponse.HasObject hasBookingRoom(int userId, int roomId) {
        List<BookRoomDTO> bookRoomDTOList = dbContext.bookRoomRepository.findAllByUserIdRoomId(userId, roomId)
                .stream().map(bookRoomMapper::toDTO)
                .filter(booking -> booking.getStatus() == BookRoomStatus.CONFIRMED ||
                        booking.getStatus() == BookRoomStatus.PAID ||
                        booking.getStatus() == BookRoomStatus.EXPIRED)
                .toList();

        return new ObjResponse.HasObject(!bookRoomDTOList.isEmpty());
    }


    //TODO Review with Account
    @Data
    public static class ReviewUser{
        private ReviewDTO review;
        private AccountDTO user;
    }
    public static class ReviewUserResponse extends BaseResponse<List<ReviewUser>>{

        public ReviewUserResponse(int status) {
            super(status);
        }

        public ReviewUserResponse(List<ReviewUser> reviewUsers) {
            super(reviewUsers);
        }
    }

    public ReviewUserResponse findAllByRoomId(int roomId){
        if(!dbContext.motelRoomRepository.existsById(roomId)) return new ReviewUserResponse(BaseResponse.ERROR);

        List<ReviewDTO> reviewDTOS = dbContext.reviewRepository.findAllByRoomId(roomId)
                .stream().map(reviewMapper::toDTO).toList();

        List<ReviewUser> data = new ArrayList<>();
        reviewDTOS.forEach(review -> {
            ReviewUser reviewUser = new ReviewUser();
            reviewUser.setReview(review);
            AccountDAO accountDAO = dbContext.accountRepository.findByBookRoomId(review.getBookRoomId());
            reviewUser.setUser(accountMapper.toDTO(accountDAO));
            data.add(reviewUser);
        });

        return new ReviewUserResponse(data);
    }

    @Autowired
    AccountMapper accountMapper;
}
