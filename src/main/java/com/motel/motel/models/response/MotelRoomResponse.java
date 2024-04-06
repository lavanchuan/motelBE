package com.motel.motel.models.response;

import com.motel.motel.models.dtos.MotelRoomDTO;

public class MotelRoomResponse extends BaseResponse<MotelRoomDTO>{
    public MotelRoomResponse(int status) {
        super(status);
    }

    public MotelRoomResponse(MotelRoomDTO motelRoomDTO) {
        super(motelRoomDTO);
    }
}
