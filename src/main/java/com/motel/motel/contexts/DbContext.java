package com.motel.motel.contexts;

import com.motel.motel.contexts.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbContext {
    @Autowired
    public AccountRepository accountRepository;

    @Autowired
    public RoleRepository roleRepository;

    @Autowired
    public PasswordRepository passwordRepository;

    @Autowired
    public MotelRoomRepository motelRoomRepository;

    @Autowired
    public MotelRepository motelRepository;

    @Autowired
    public MakeAppointRepository makeAppointRepository;

    @Autowired
    public BookRoomRepository bookRoomRepository;

    @Autowired
    public ReviewRepository reviewRepository;

    @Autowired
    public MessageRepository messageRepository;

    @Autowired
    public NotificationRepository notificationRepository;

    @Autowired
    public ImageRepository imageRepository;
}
