package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.BookRoomDTO;
import com.motel.motel.models.mapper.BookRoomMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.BookRoomResponse;
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

    @Override
    public BookRoomResponse add(BookRoomDTO bookRoomDTO) {
        if (dbContext.bookRoomRepository.existsById(bookRoomDTO.getId()))
            return new BookRoomResponse(BaseResponse.ERROR);

        if(bookRoomDTO.getCreateAt() == null || bookRoomDTO.getCreateAt().isEmpty()) bookRoomDTO.setCreateAt(LocalDateTime.now());

        dbContext.bookRoomRepository.save(bookRoomMapper.toDAO(bookRoomDTO, dbContext));

        List<BookRoomDTO> result = findAll().stream().filter(bookRoom -> bookRoom.getUserId() == bookRoomDTO.getUserId())
                .collect(Collectors.toList());

        return new BookRoomResponse(result);
    }

    @Override
    public BookRoomResponse update(BookRoomDTO bookRoomDTO) {
        return null;
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
    public BookRoomDTO findById(Integer integer) {
        return null;
    }
}
