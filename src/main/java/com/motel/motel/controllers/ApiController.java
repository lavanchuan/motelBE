package com.motel.motel.controllers;

import com.motel.motel.models.dtos.MessageDTO;
import com.motel.motel.models.dtos.NotificationDTO;
import com.motel.motel.models.e.MessageStatus;
import com.motel.motel.models.e.NotificationStatus;
import com.motel.motel.services.AdminService;
import com.motel.motel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    AdminService adminService;

    @GetMapping("/motel-room-all")
    public ResponseEntity<?> motelRoomFindAll() {
//        return ResponseEntity.ok(adminService.motelRoomService.findAll());
        return ResponseEntity.ok(adminService.motelRoomService.findAllInfo());
    }

    @GetMapping("/room-by-id")
    public ResponseEntity<?> motelRoomFindOne(@RequestParam(name = "roomId", required = true) int roomId) {
        return ResponseEntity.ok(adminService.motelRoomService.findById(roomId));
    }

    //TODO notification
    @GetMapping("/notification-count-received")
    public ResponseEntity<?> countNotificationReceived(@RequestParam(name = "receiverId") int receiverId) {
        return ResponseEntity.ok(adminService.notificationService.countNotificationReceived(receiverId));
    }

    @GetMapping("/notification-all")
    public ResponseEntity<?> findAllNotification(@RequestParam(name = "receiverId") int receiverId) {
        return ResponseEntity.ok(adminService.notificationService.findAllByReceiverId(receiverId));
    }

    @PutMapping("/read-notification")
    public ResponseEntity<?> readNotification(@RequestBody NotificationDTO request) {
        request.setStatus(NotificationStatus.READ);
        adminService.notificationService.update(request);
        return findAllNotification(request.getReceiverId());
    }

    @PutMapping("/read-message")
    public ResponseEntity<?> readMessage(@RequestBody MessageDTO request) {
        request.setStatus(MessageStatus.READ);
        return ResponseEntity.ok(adminService.messageService.update(request));
    }

    // Find Message All By SenderId
    @GetMapping("/find-message-all-by-sender-id")
    public ResponseEntity<?> findMessageAllBySenderId(@RequestParam(name = "senderId") int senderId) {
        return ResponseEntity.ok(adminService.messageService.findMessageAllOfSender(senderId));
    }

    @GetMapping("message-all-by-user-id")
    public ResponseEntity<?> messageAllByUserId(@RequestParam(name = "userId") int userId){
        return ResponseEntity.ok(adminService.messageService.messageAllByUserId(userId));
    }

    @GetMapping("/message-all")
    public ResponseEntity<?> messageAllSenderAndReceiver(@RequestParam(name = "senderId") int senderId,
                                                         @RequestParam(name = "receiverId") int receiverId) {
        return ResponseEntity.ok(adminService.messageService.messageAllSenderAndReceiver(senderId, receiverId));
    }

    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@RequestBody MessageDTO request) {
        return ResponseEntity.ok(adminService.messageService.add(request));
    }

    @GetMapping("/message-count-received")
    public ResponseEntity<?> countMessageReceived(@RequestParam(name = "receiverId") int receiverId) {
        return ResponseEntity.ok(adminService.messageService.countMessageReceived(receiverId));
    }

    //TODO CHECK
    @GetMapping("/admin")
    public ResponseEntity<?> admin() {
        return ResponseEntity.ok(adminService.accountService.findAllAdmin());
    }

    //TODO Image
    @GetMapping("/image-all")
    public ResponseEntity<?> findAllImageByRoomId(@RequestParam(name = "roomId") int roomId) {
        return ResponseEntity.ok(adminService.imageService.findAllByRoomId(roomId));
    }

    //TODO search and filter
    @GetMapping("/search-room-by-address-and-price")
    public ResponseEntity<?> searchRoomByAddressAndPrice(
            @RequestParam(name = "address") String address,
            @RequestParam(name = "minPrice", defaultValue = "-1") float minPrice,
            @RequestParam(name = "maxPrice", defaultValue = "-1") float maxPrice) {
        return ResponseEntity.ok(adminService.motelRoomService
                .searchByAddressAndPrice(address, minPrice, maxPrice));
    }

    //TODO review
    @GetMapping("/review-all") 
    public ResponseEntity<?> reviewAllOfRoom(@RequestParam(name = "roomId") int roomId){
        return ResponseEntity.ok(adminService.reviewService.findAllByRoomId(roomId).getData());
    }

}
