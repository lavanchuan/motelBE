package com.motel.motel.controllers;

import com.motel.motel.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    AdminService adminService;

    @GetMapping("/motel-room-all")
    public ResponseEntity<?> motelRoomFindAll(){
//        return ResponseEntity.ok(adminService.motelRoomService.findAll());
        return ResponseEntity.ok(adminService.motelRoomService.findAllInfo());
    }

    @GetMapping("/room-by-id")
    public ResponseEntity<?> motelRoomFindOne(@RequestParam(name = "roomId", required = true) int roomId){
        return ResponseEntity.ok(adminService.motelRoomService.findById(roomId));
    }

    // REVIEW BY ROOM ID
    @GetMapping("/review-all-by-room")
    public ResponseEntity<?> reviewFindAllByRoomId(@RequestParam(name = "motelRoomId") int motelRoomId){
        return ResponseEntity.ok(adminService.reviewService.findAllByRoomId(motelRoomId));
    }

    // Find Message All By SenderId
    @GetMapping("/find-message-all-by-sender-id")
    public ResponseEntity<?> findMessageAllBySenderId(@RequestParam(name = "senderId") int senderId){
        return ResponseEntity.ok(adminService.messageService.findMessageAllOfSender(senderId));
    }

    //TODO CHECK
    @GetMapping("/admin")
    public ResponseEntity<?> admin(){
        return ResponseEntity.ok(adminService.accountService.findAllAdmin());
    }

    //TODO Image
    @GetMapping("/image-all")
    public ResponseEntity<?> findAllImageByRoomId(@RequestParam(name = "roomId") int roomId){
        return ResponseEntity.ok(adminService.imageService.findAllByRoomId(roomId));
    }

}
