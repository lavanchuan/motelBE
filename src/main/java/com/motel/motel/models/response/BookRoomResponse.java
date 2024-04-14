package com.motel.motel.models.response;

import com.motel.motel.models.dtos.BookRoomDTO;
import lombok.Data;

import java.util.List;

public class BookRoomResponse extends BaseResponse<List<BookRoomDTO>>{
    public BookRoomResponse(int status) {
        super(status);
    }

    public BookRoomResponse(List<BookRoomDTO> bookRoomDTOS) {
        super(bookRoomDTOS);
    }
}
