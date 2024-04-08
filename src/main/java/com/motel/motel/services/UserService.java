package com.motel.motel.services;

import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.MakeAppointDTO;
import com.motel.motel.models.dtos.MotelDTO;
import com.motel.motel.models.dtos.MotelRoomDTO;
import com.motel.motel.models.e.RoleName;
import com.motel.motel.models.e.RoomStatus;
import com.motel.motel.models.entities.MakeAppointDAO;
import com.motel.motel.models.mapper.AccountMapper;
import com.motel.motel.models.request.BookingAppointRequest;
import com.motel.motel.models.response.AccountResponse;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.MotelRoomResponse;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AdminService adminService;

    public BaseResponse<?> infoOwner(int ownerId){
        if(!isOwner(ownerId)) return new AccountResponse(BaseResponse.ERROR);

        return new AccountResponse(adminService.accountService.findById(ownerId));
    }

    private boolean isOwner(int ownerId) {
        if(!adminService.accountService.existsById(ownerId)) return false;
        return adminService.accountService.findById(ownerId).getRole() == RoleName.OWNER;
    }

    private boolean isUser(int userId) {
        if(!adminService.accountService.existsById(userId)) return false;
        return adminService.accountService.findById(userId).getRole() == RoleName.USER;
    }

    public BaseResponse<?> infoRoom(int roomId) {
        if(!adminService.motelRoomService.existsById(roomId))
            return new MotelRoomResponse(BaseResponse.ERROR);

        RoomOwnerResponse data = new RoomOwnerResponse();
        data.setRoom(adminService.motelRoomService.findById(roomId));
        data.setOwner(adminService.accountService.findByRoomId(roomId));

        return new Response(data);
    }

    public BaseResponse<?> bookingAppoint(MakeAppointDTO request) {
        if(!isUser(request.getUserId())) return new Response(IS_NOT_USER);
        if(!isValidMeetTime(request.getMotelRoomId(), DateTimeFormatService.toLocalDateTime(request.getMeetTime())))
            return new Response(IS_NOT_VALID_MEET_TIME);
        if(!isEmptyRoom(request.getMotelRoomId()))
            return new Response((IS_NOT_ROOM_EMPTY));

        return adminService.makeAppointService.add(request);
    }

    private boolean isValidMeetTime(int motelRoomId, LocalDateTime meetTime) {

        //if(meetTime.isBefore(LocalDateTime.now())) return false; // [after now]

        try{
            adminService.makeAppointService.findAll()
                    .forEach(appoint ->{
                        if(!isValidMeetTime(meetTime,
                                Objects.requireNonNull(DateTimeFormatService.toLocalDateTime(appoint.getMeetTime()))))
                            throw new RuntimeException();
                    });
        } catch (RuntimeException e){
            return false;
        }

        return true;
    }

    private boolean isValidMeetTime(LocalDateTime timeDTO, LocalDateTime timeDAO) {
        return timeDAO.isBefore(timeDTO.plusMinutes(-1 * INTERVAL_TIME_APPOINT_MINUTES))
                || timeDAO.isAfter(timeDTO.plusMinutes(INTERVAL_TIME_APPOINT_MINUTES));
    }

    private boolean isEmptyRoom(int motelRoomId) {
        return adminService.motelRoomService.findById(motelRoomId)
                .getStatus() == RoomStatus.VACANT_ROOM;
    }

    // CLASS
    @Data
    public static class RoomOwnerResponse{
        private AccountDTO owner;
        private MotelRoomDTO room;
        private MotelDTO motel;
    }

    public static class Response extends BaseResponse<RoomOwnerResponse>{

        public Response(int status) {
            super(status);
        }

        public Response(RoomOwnerResponse roomOwnerResponse) {
            super(roomOwnerResponse);
        }
    }

    //
    public static final int INTERVAL_TIME_APPOINT_MINUTES = 60; //minute
    // STATUS
    public static final int IS_NOT_USER = 499;
    public static final int IS_NOT_VALID_MEET_TIME = 498;
    public static final int IS_NOT_ROOM_EMPTY = 597;
}
