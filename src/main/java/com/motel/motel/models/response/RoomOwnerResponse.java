package com.motel.motel.models.response;

import com.motel.motel.services.UserService;

public class RoomOwnerResponse extends BaseResponse<UserService.RoomOwnerResponse>{
    public RoomOwnerResponse(int status) {
        super(status);
    }

    public RoomOwnerResponse(UserService.RoomOwnerResponse roomOwnerResponse) {
        super(roomOwnerResponse);
    }
}
