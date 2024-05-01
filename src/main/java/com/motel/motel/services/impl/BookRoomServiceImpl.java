package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.BookRoomDTO;
import com.motel.motel.models.dtos.MotelDTO;
import com.motel.motel.models.dtos.MotelRoomDTO;
import com.motel.motel.models.entities.BookRoomDAO;
import com.motel.motel.models.mapper.AccountMapper;
import com.motel.motel.models.mapper.BookRoomMapper;
import com.motel.motel.models.mapper.MotelMapper;
import com.motel.motel.models.mapper.MotelRoomMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.BookRoomResponse;
import com.motel.motel.models.response.ObjResponse;
import com.motel.motel.services.AppSystemService;
import com.motel.motel.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRoomServiceImpl implements ICRUDService<BookRoomDTO, Integer, BookRoomResponse> {

    @Autowired
    DbContext dbContext;

    @Autowired
    BookRoomMapper bookRoomMapper;

    @Autowired
    AppSystemService appSystemService;

    @Autowired
    AccountServiceImpl accountService;
    @Autowired
    MotelServiceImpl motelService;
    @Autowired
    MotelRoomServiceImpl motelRoomService;

    //OTHER MAPPER
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    MotelRoomMapper roomMapper;
    @Autowired
    MotelMapper motelMapper;

    @Override
    public BookRoomResponse add(BookRoomDTO bookRoomDTO) {
        if (dbContext.bookRoomRepository.existsById(bookRoomDTO.getId()))
            return new BookRoomResponse(BaseResponse.ERROR);

        if (bookRoomDTO.getCreateAt() == null || bookRoomDTO.getCreateAt().isEmpty())
            bookRoomDTO.setCreateAt(LocalDateTime.now());

        BookRoomDAO dao = dbContext.bookRoomRepository.save(bookRoomMapper.toDAO(bookRoomDTO, dbContext));

        //send mail and notific
        BookRoomDTO detail = bookRoomMapper.toDTO(dao);
        MotelRoomDTO room = motelRoomService.findById(detail.getMotelRoomId());
        AccountDTO user = accountService.findById(detail.getUserId());
        MotelDTO motel = motelService.findById(room.getMotelId());
        String receiver = accountService.findById(motel.getOwnerId()).getMail();
        appSystemService.sendMailOwnerForBookRoom(detail, room, user, motel, receiver);

        List<BookRoomDTO> result = findAll().stream().filter(bookRoom -> bookRoom.getUserId() == bookRoomDTO.getUserId())
                .collect(Collectors.toList());

        return new BookRoomResponse(result);
    }

    @Override
    public BookRoomResponse update(BookRoomDTO bookRoomDTO) {
        if (!dbContext.bookRoomRepository.existsById(bookRoomDTO.getId()))
            return new BookRoomResponse(BaseResponse.ERROR);
        bookRoomMapper.toDTO(dbContext.bookRoomRepository.save(bookRoomMapper.toDAO(bookRoomDTO, dbContext)));
        return new BookRoomResponse(findAll().stream().filter(bookRoom -> bookRoom.getUserId() == bookRoomDTO.getUserId())
                .toList());
    }

    @Override
    public BookRoomResponse delete(Integer integer) {
        return null;
    }

    @Override
    public List<BookRoomDTO> findAll() {
        return dbContext.bookRoomRepository.findAll()
                .stream().map(bookRoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public BookRoomDTO findById(Integer id) {
        return bookRoomMapper.toDTO(dbContext.bookRoomRepository.findById(id).orElseThrow());
    }

    public List<?> findAllByUserId(int userId) {
        List<ObjResponse.BookingDetail> response = new ArrayList<>();

        List<BookRoomDTO> bookings = findAll().stream()
                .filter(booking -> booking.getUserId() == userId)
                .toList();

        for (BookRoomDTO booking : bookings) {
            ObjResponse.BookingDetail obj = new ObjResponse.BookingDetail();

            obj.setBooking(booking);

            obj.setRoom(roomMapper.toDTO(dbContext.motelRoomRepository
                    .findById(booking.getMotelRoomId()).orElseThrow()));

            obj.setMotel(motelMapper.toDTO(dbContext.motelRepository
                    .findById(obj.getRoom().getMotelId()).orElseThrow()));

            obj.setOwner(accountMapper.toDTO(dbContext.accountRepository
                    .findById(obj.getMotel().getOwnerId()).orElseThrow()));

            response.add(obj);
        }

        return response;
    }

    public List<BookRoomDTO> findAllBookingByOwnerId(int ownerId) {
        return dbContext.bookRoomRepository.findAllBookingByOwnerId(ownerId)
                .stream().map(bookRoomMapper::toDTO)
                .toList();
    }
}
