package com.motel.motel.controllers;

import com.motel.motel.models.dtos.*;
import com.motel.motel.models.response.*;
import com.motel.motel.services.AdminService;
import com.motel.motel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //TODO APPOINT
    @GetMapping("/appoint-all")
    public ResponseEntity<?> findAllAppointByUserId(@RequestParam(name = "userId") int userId){
        return ResponseEntity.ok(adminService.makeAppointService.findAllByUserId(userId));
    }

    @PutMapping("/cancel-appoint")
    public ResponseEntity<?> cancelAppoint(@RequestParam(name = "appointId") int appointId){
        return ResponseEntity.ok(adminService.makeAppointService.cancelAppoint(appointId));
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

    //TODO BookRoom
    @GetMapping("/booking-all")
    public ResponseEntity<?> bookingAllByUserId(@RequestParam(name = "userId") int userId){
        return ResponseEntity.ok(adminService.bookRoomService.findAllByUserId(userId));
    }

    @PostMapping("/booking-room") // return list booking of userId
    public ResponseEntity<?> bookingRoom(@RequestBody BookRoomDTO request){
        BookRoomResponse response = userService.bookingRoom(request);

        switch (response.getStatus()){
            case BaseResponse.ERROR: return ResponseEntity.badRequest().build();
            default: return ResponseEntity.ok(response);
        }
    }



    //TODO Review
    @PostMapping("/review-add")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO request){
        ReviewResponse response = userService.addReview(request);

        switch (response.getStatus()){
            case BaseResponse.ERROR: return ResponseEntity.badRequest().build();
            default: return ResponseEntity.ok(response);
        }
    }

    @GetMapping("last-booking-room")
    public ResponseEntity<?> lastBookingRoom(@RequestParam(name = "userId") int userId,
                                               @RequestParam(name = "roomId") int roomId){
        return ResponseEntity.ok(adminService.bookRoomService.lastBookingRoom(userId, roomId));
    }

    @GetMapping("/has-booking-room")
    ResponseEntity<?> hasBookingRoom(@RequestParam(name = "userId") int userId,
                                     @RequestParam(name = "roomId") int roomId) {
        return ResponseEntity.ok(adminService.reviewService.hasBookingRoom(userId, roomId));
    }

    @PostMapping("/send-mess-admin")
    public ResponseEntity<?> sendMessageAdmin(@RequestBody MessageDTO request){

        return ResponseEntity.ok(userService.sendMessageAdmin(request));
    }

    @PostMapping("/send-mess-owner")
    public ResponseEntity<?> sendMessageOwner(@RequestBody MessageDTO request){

        return ResponseEntity.ok(userService.sendMessageOwner(request));
    }


    // MESSAGE
    @GetMapping("/message")
    public ResponseEntity<?> message(@RequestParam(name = "senderId") int senderId,
                                     @RequestParam(name = "receiverId") int receiverId){

        return ResponseEntity.ok(adminService.messageService.findAllBySenderReceiver(senderId, receiverId));
    }


}
