package com.motel.motel.controllers;

import com.motel.motel.models.dtos.*;
import com.motel.motel.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    OwnerService ownerService;

    //TODO motel

    @GetMapping("/count-motel-activate")
    public ResponseEntity<?> countMotelActivate(@RequestParam(name = "ownerId") int ownerId) {
        return ResponseEntity.ok(ownerService.countMotelActivate(ownerId));
    }

    @GetMapping("/motel-all")
    public ResponseEntity<?> findAll(@RequestParam(name = "ownerId") int ownerId) {
        return ResponseEntity.ok(ownerService.findAllMotelByOwner(ownerId));
    }

    @PostMapping("/motel-add")
    public ResponseEntity<?> addMotel(@RequestBody MotelDTO request) {
        return ResponseEntity.ok(ownerService.addMotel(request));
    }

    @PutMapping("/motel-update")
    public ResponseEntity<?> updateMotel(@RequestBody MotelDTO request) {
        return ResponseEntity.ok(ownerService.updateMotel(request));
    }

    @GetMapping("/motel-room-all")
    public ResponseEntity<?> motelRoomFindAll(@RequestParam(name = "motelId") int motelId) {
        return ResponseEntity.ok(ownerService.motelRoomFindAll(motelId));
    }

    @PostMapping("/motel-room-add")
    public ResponseEntity<?> addMotelRoom(@RequestBody MotelRoomDTO request) {
        return ResponseEntity.ok(ownerService.addMotelRoom(request));
    }

    @PutMapping("/motel-room-update")
    public ResponseEntity<?> updateMotelRoom(@RequestBody MotelRoomDTO request) {
        return ResponseEntity.ok(ownerService.updateMotelRoom(request));
    }

    //TODO booking
    @GetMapping("/booking-all")
    public ResponseEntity<?> findAllBookingByOwnerId(@RequestParam(name = "ownerId") int ownerId) {
        return ResponseEntity.ok(ownerService.findAllBookingByOwnerId(ownerId));
    }

    @PutMapping("/book-room-confirm")
    public ResponseEntity<?> confirmBookRoom(@RequestBody BookRoomDTO request) {
        return ResponseEntity.ok(ownerService.confirmBookRoomRequest(request, true));
    }

    @PutMapping("/book-room-reject")
    public ResponseEntity<?> rejectBookRoom(@RequestBody BookRoomDTO request) {
        return ResponseEntity.ok(ownerService.confirmBookRoomRequest(request, false));
    }

    //TODO Make Appoint
    @GetMapping("/make-appoint-all")
    public ResponseEntity<?> findAllMakeAppoint(@RequestParam(name = "ownerId") int ownerId) {
        return ResponseEntity.ok(ownerService.findAllMakeAppoint(ownerId));
    }

    @PutMapping("/make-appoint-confirm")
    public ResponseEntity<?> confirmMakeAppoint(@RequestBody MakeAppointDTO request) {
        return ResponseEntity.ok(ownerService.confirmMakeAppoint(request, true));
    }

    @PutMapping("/make-appoint-reject")
    public ResponseEntity<?> rejectMakeAppoint(@RequestBody MakeAppointDTO request) {
        return ResponseEntity.ok(ownerService.confirmMakeAppoint(request, false));
    }

    @PostMapping("/send-message-user")
    public ResponseEntity<?> sendMessageUser(@RequestBody MessageDTO request) {
        return ResponseEntity.ok(ownerService.sendMessageUser(request));
    }

    @PostMapping("/send-message-admin")
    public ResponseEntity<?> sendMessageAdmin(@RequestBody MessageDTO request) {
        return ResponseEntity.ok(ownerService.sendMessageAdmin(request));
    }

    @GetMapping("/find-all-receiver")
    public ResponseEntity<?> findAllReceiver(@RequestParam(name = "ownerId") int ownerId) {
        return ResponseEntity.ok(ownerService.findAllReceiverByOwnerId(ownerId));
    }

    @GetMapping("/find-all-message-with-receiver")
    public ResponseEntity<?> findAllMessageWithReceiver(@RequestParam(name = "ownerId") int ownerId,
                                                        @RequestParam(name = "receiverId") int receiverId) {
        return ResponseEntity.ok(ownerService.findAllMessageByOwnerIdReceiverId(ownerId, receiverId));
    }

    //TODO image
    @PostMapping("/room-image-add")
    public ResponseEntity<?> addRoomImage(@RequestBody ImageDTO request){
        return ResponseEntity.ok(ownerService.addRoomImage(request));
    }
}