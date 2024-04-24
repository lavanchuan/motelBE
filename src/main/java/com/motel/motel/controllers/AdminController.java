package com.motel.motel.controllers;

import com.motel.motel.models.request.ConfirmRequest;
import com.motel.motel.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    //TODO find all user
    @GetMapping("/find-all-user")
    public ResponseEntity<?> findAllUser(){
        return ResponseEntity.ok(adminService.accountService.findAllUser());
    }

    //TODO find all owner
    @GetMapping("/find-all-owner")
    public ResponseEntity<?> findAllOwner(){
        return ResponseEntity.ok(adminService.accountService.findAllOwner());
    }

    //TODO all room with add request
    @GetMapping("/find-all-room-add")
    public ResponseEntity<?>findAllRoomAdd(){
        return ResponseEntity.ok(adminService.findAllRoomAdd());
    }

    //TODO confirm regis owner request
    @PutMapping("/confirm-regis-owner")
    public ResponseEntity<?> confirmRegisOwner(@RequestBody ConfirmRequest request){
        return ResponseEntity.ok(adminService.confirmRegisOwnerRequest(request.getId(), true));
    }

    //TODO reject regis owner request
    @PutMapping("/reject-regis-owner")
    public ResponseEntity<?> rejectRegisOwner(@RequestBody ConfirmRequest request){
        return ResponseEntity.ok(adminService.confirmRegisOwnerRequest(request.getId(), false));
    }

    //TODO confirm add motel
    @PutMapping("/confirm-add-motel")
    public ResponseEntity<?> confirmAddMotel(@RequestBody ConfirmRequest request){
        return ResponseEntity.ok(adminService.confirmAddMotel(request.getId(), true));
    }

    //TODO reject add motel
    @PutMapping("/reject-add-motel")
    public ResponseEntity<?> rejectAddMotel(@RequestBody ConfirmRequest request){
        return ResponseEntity.ok(adminService.confirmAddMotel(request.getId(), false));
    }

    //TODO confirm add room
    @PutMapping("/confirm-add-room")
    public ResponseEntity<?> confirmAddRoom(@RequestBody ConfirmRequest request,
                                            @RequestParam(name = "adminId") int adminId){
        return ResponseEntity.ok(adminService.confirmCreateRoomRequest(request, adminId, true));
    }

    //TODO reject add room
    @PutMapping("/reject-add-room")
    public ResponseEntity<?> rejectAddRoom(@RequestBody ConfirmRequest request,
                                           @RequestParam(name = "adminId") int adminId){
        return ResponseEntity.ok(adminService.confirmCreateRoomRequest(request, adminId, false));
    }


}
