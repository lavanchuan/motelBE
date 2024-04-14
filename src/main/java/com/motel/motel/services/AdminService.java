package com.motel.motel.services;

import com.motel.motel.models.dtos.ReviewDTO;
import com.motel.motel.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    @Autowired
    public AccountServiceImpl accountService;

    @Autowired
    public PasswordServiceImpl passwordService;

    @Autowired
    public MotelRoomServiceImpl motelRoomService;

    @Autowired
    public MakeAppointServiceImpl makeAppointService;

    @Autowired
    public BookRoomServiceImpl bookRoomService;

    @Autowired
    public ReviewServiceImpl reviewService;

    @Autowired
    public MessageServiceImpl messageService;
}
