package com.motel.motel.services;

import com.motel.motel.services.impl.AccountServiceImpl;
import com.motel.motel.services.impl.MakeAppointServiceImpl;
import com.motel.motel.services.impl.MotelRoomServiceImpl;
import com.motel.motel.services.impl.PasswordServiceImpl;
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

}
