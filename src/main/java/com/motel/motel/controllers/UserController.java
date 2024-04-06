package com.motel.motel.controllers;

import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.MakeAppointDTO;
import com.motel.motel.models.response.AccountResponse;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.services.AdminService;
import com.motel.motel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @GetMapping("/info-owner")
    public ResponseEntity<?> infoOwner(@RequestParam(name = "ownerId", required = true) int ownerId){
        BaseResponse<?> response = userService.infoOwner(ownerId);

        switch (response.getStatus()){
            case BaseResponse.SUCCESS:
                return ResponseEntity.ok(response);
            default: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/info-room")
    public ResponseEntity<?> infoRoom(@RequestParam(name = "roomId", required = true) int roomId){
        BaseResponse<?> response = userService.infoRoom(roomId);

        if(response.getStatus() == BaseResponse.ERROR)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/booking-appoint")
    public ResponseEntity<?> bookAppoint(@RequestBody MakeAppointDTO request){
        BaseResponse<?> response = userService.bookingAppoint(request);

        switch (response.getStatus()){
            case UserService.IS_NOT_USER:
            case UserService.IS_NOT_ROOM_EMPTY:
            case UserService.IS_NOT_VALID_MEET_TIME:
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            default: return ResponseEntity.ok(response);
        }
    }
}
