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
}
