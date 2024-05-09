package com.motel.motel.services;

import com.motel.motel.models.dtos.*;
import com.motel.motel.models.entities.AccountDAO;
import com.motel.motel.services.impl.AccountServiceImpl;
import com.motel.motel.services.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.DateFormatter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;

@Service
public class AppSystemService {

    @Autowired
    MailSenderService mailSenderService;

    @Autowired
    AccountServiceImpl accountService;

    @Autowired
    NotificationServiceImpl notificationService;

    public void sendMailRegisOwner(String userEmail) {
        MailDTO mailDTO = new MailDTO();
        mailDTO.setSubject("Regis owner");

        AccountDTO dto = accountService.findByMail(userEmail);
        String birthday = dto.getDateOfBirth().getDayOfMonth() + "/" +
                dto.getDateOfBirth().getMonthValue() + "/" +
                dto.getDateOfBirth().getYear();

        mailDTO.setContent(String.format("Regis owner form %s\n" +
                "Name: %s\n" +
                "Phone: %s\n" +
                "Address: %s\n" +
                "Birthday: %s\n",
                getString(userEmail),
                getString(dto.getName()),
                getString(dto.getPhone()),
                getString(dto.getAddress()),
                getString(birthday)));

        accountService.findAllAdmin().forEach(admin -> {
            mailDTO.setTo(admin.getMail());
            mailSenderService.sendMail(mailDTO);
        });
    } //TODO sendAdminMailForUserRegisOwner

    public void sendMailOwnerForBookRoom(BookRoomDTO detail, MotelRoomDTO room, AccountDTO user, MotelDTO motel, String receiver) {
        if (!accountService.existsByMail(receiver)) return;

        MailDTO mail = new MailDTO();
        mail.setTo(receiver);
        mail.setSubject("Book room");


        String content = String.format("New book room from '%s'\n", receiver) +
                String.format("ROOM INFO\nName: %s\n", room.getName() == null ? "" : room.getName()) +
                String.format("Area: %s\n", room.getArea() == null ? "" : room.getArea()) +
                String.format("Electric: price '%s'\n", motel.getElectric_price() == null ? "" : motel.getElectric_price()) +
                String.format("Water price: '%s'\n", motel.getWater_price() == null ? "" : motel.getWater_price()) +
                String.format("Price: %f\n", room.getPrice()) +
                String.format("Sale: %d\n", room.getSale()) +
                String.format("Sale price: %f\n", room.getPrice() * (100 - room.getSale()) / 100) +
                String.format("Address: %s\n", motel.getAddress() == null ? "" : motel.getAddress()) +
                String.format("Facility: %s\n\n", room.getFacility() == null ? "" : room.getFacility()) +
                String.format("USER INFO\nName: %s", user.getName() == null ? "" : user.getName()) +
                String.format("Mail: %s\n", user.getMail() != null ? user.getMail() : "") +
                String.format("Phone: %s\n", user.getPhone() != null ? user.getPhone() : "") +
                String.format("Address: %s\n", user.getAddress() != null ? user.getAddress() : "") +
                String.format("Sex: %s\n", user.getSex() != null ? user.getSex().toString() : "") +
                String.format("Birthday: %s\n\n", user.getDateOfBirth() == null ? "" : user.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) +
                String.format("BOOK ROOM DETAIL\nCreate at: '%s'\n", detail.getCreateAt() == null ? "" : detail.getCreateAt()) +
                String.format("Start time: '%s'\n", detail.getStartTime() == null ? "" : detail.getStartTime()) +
                String.format("End time: '%s'\n", detail.getEndTime() == null ? "" : detail.getEndTime());

        mail.setContent(content);

        mailSenderService.sendMail(mail);

        sendOwnerNotificationForUserBookRoom(accountService.findByMail(receiver).getId());
    }

    public void sendMailOwnerForBookAppoint(MakeAppointDTO detail, MotelRoomDTO room, AccountDTO user, MotelDTO motel, String receiver) {
        if (!accountService.existsByMail(receiver)) return;

        MailDTO mail = new MailDTO();
        mail.setTo(receiver);
        mail.setSubject("Make Appoint");


        String content = String.format("New book room from '%s'\n", receiver) +
                String.format("ROOM INFO\nName: %s\n", getString(room.getName())) +
                String.format("Area: %s\n", getString(room.getArea())) +
                String.format("Electric: price '%s'\n", getString(motel.getElectric_price())) +
                String.format("Water price: '%s'\n", getString(motel.getWater_price())) +
                String.format("Price: %f\n", room.getPrice()) +
                String.format("Sale: %d\n", room.getSale()) +
                String.format("Sale price: %f\n", room.getPrice() * (100 - room.getSale()) / 100) +
                String.format("Address: %s\n", getString(motel.getAddress())) +
                String.format("Facility: %s\n\n", getString(room.getFacility())) +
                String.format("USER INFO\nName: %s", getString(user.getName())) +
                String.format("Mail: %s\n", getString(user.getMail())) +
                String.format("Phone: %s\n", getString(user.getPhone())) +
                String.format("Address: %s\n", getString(user.getAddress())) +
                String.format("Sex: %s\n", user.getSex() == null ? "" : user.getSex().toString()) +
                String.format("Birthday: %s\n\n", user.getDateOfBirth() == null ? "" : user.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))) +
                String.format("BOOK APPOINT DETAIL\nCreate at: '%s'\n", getString(detail.getCreateAt())) +
                String.format("Meet time: '%s'\n", getString(detail.getMeetTime()));

        mail.setContent(content);

        mailSenderService.sendMail(mail);
        sendOwnerNotificationForMakeAppoint(accountService.findByMail(receiver).getId());
    }

    public void sendOwnerNotificationForUserBookRoom(int ownerId) {
        if (!accountService.existsById(ownerId)) {
            System.err.println(String.format("ERROR SEND NOTIFICATION ownerId(%d)", ownerId));
            return;
        }

        int adminId = 0;
        if (!accountService.findAllAdmin().isEmpty())
            adminId = accountService.findAllAdmin().get(0).getId();

        NotificationDTO dto = new NotificationDTO();
        dto.setReceiverId(ownerId);
        dto.setSenderId(adminId);
        dto.setMessage("Bạn có một yêu cầu thuê phòng trọ mới");
        dto.setNavigate("/owner-home");//TODO NAVIGATE CLIENT
        notificationService.add(dto);
    }

    public void sendOwnerNotificationForMakeAppoint(int ownerId) {
        if (!accountService.existsById(ownerId)) {
            System.err.println(String.format("ERROR SEND NOTIFICATION ownerId(%d)", ownerId));
            return;
        }

        int adminId = 0;
        if (!accountService.findAllAdmin().isEmpty())
            adminId = accountService.findAllAdmin().get(0).getId();

        NotificationDTO dto = new NotificationDTO();
        dto.setReceiverId(ownerId);
        dto.setSenderId(adminId);
        dto.setMessage("Bạn có một yêu cầu xem trọ cần phản hồi cho khách hàng");
        dto.setNavigate("/owner-home");//TODO NAVIGATE CLIENT
        notificationService.add(dto);
    }

    public void sendUserMailForOwnerConfirmBookRoom(BookRoomDTO bookRoomDTO, boolean isConfirm) {
        if (!accountService.existsById(bookRoomDTO.getUserId())) return;
        MailDTO mail = new MailDTO();

        mail.setTo(accountService.findById(bookRoomDTO.getUserId()).getMail());
        mail.setSubject(String.format("Book room %s", isConfirm ? "confirmed" : "reject"));
        String content = isConfirm ? "You room booking request has been confirmed by owner" : bookRoomDTO.getReason();
        if (isConfirm) content += "\nDetail:\n" +
                String.format("Start time: %s\n", getString(bookRoomDTO.getStartTime())) +
                String.format("End time: %s\n", getString(bookRoomDTO.getEndTime()));
        mail.setContent(content);

        mailSenderService.sendMail(mail);
    }

    public void sendUserMailForOwnerConfirmBookAppoint(MakeAppointDTO makeAppointDTO, boolean isConfirm) {
        if (!accountService.existsById(makeAppointDTO.getUserId())) return;
        MailDTO mail = new MailDTO();

        mail.setTo(accountService.findById(makeAppointDTO.getUserId()).getMail());
        mail.setSubject(String.format("Make Appoint %s", isConfirm ? "confirmed" : "rejected"));
        String content = isConfirm ? "You appoint booking request has been confirmed by owner" : makeAppointDTO.getReason();
        if (isConfirm) content += "\nDetail:\n" +
                String.format("Meet time: %s\n", makeAppointDTO.getMeetTime());
        mail.setContent(content);

        mailSenderService.sendMail(mail);
    }

    public void sendUserNotificationForOwnerConfirmBookRoom(int receiverId, boolean isConfirm) {
        NotificationDTO dto = new NotificationDTO();
        dto.setMessage(String.format("Yêu cầu thuê phòng trọ của bạn đã %s",
                isConfirm?"được chấp nhận":"bị từ chối"));
        dto.setNavigate("/user-info");
        dto.setReceiverId(receiverId);
        notificationService.add(dto);
    }

    public void sendUserNotificationForOwnerConfirmBookAppoint(int receiverId, boolean isConfirm) {
        NotificationDTO dto = new NotificationDTO();
        dto.setMessage(String.format("Yêu cầu xem phòng trọ của bạn đã %s",
                isConfirm?"được chấp nhận":"bị từ chối"));
        dto.setNavigate("/user-info");
        dto.setReceiverId(receiverId);
        notificationService.add(dto);
    }

    public void sendAdminMailForOwnerAddRoom(AccountDTO owner, MotelRoomDTO room, MotelDTO motel) {

        MailDTO mailDTO = new MailDTO();
        mailDTO.setSubject(String.format("Add room from %s", getString(owner.getMail())));
        String content = "Add room detail:\n";
        content += String.format("Owner: %s\n", getString(owner.getMail())) +
                String.format("Motel name: %s\n", getString(motel.getName())) +
                String.format("Motel address: %s\n", getString(motel.getAddress())) +
                String.format("Electric price: %s\n", getString(motel.getElectric_price())) +
                String.format("Water price: %s\n", getString(motel.getWater_price())) +
                "ROOM DETAIL:\n" +
                String.format("\tName: %s\n", getString(room.getName())) +
                String.format("\tArea: %s\n", getString(room.getArea())) +
                String.format("\tPrice: %f\n", room.getPrice()) +
                String.format("\tSale: %d", room.getSale()) + "%\n" +
                String.format("\tFacility: %s\n", getString(room.getFacility()));

        mailDTO.setContent(content);

        for(AccountDTO admin : accountService.findAllAdmin()){
            mailDTO.setTo(admin.getMail());
            mailSenderService.sendMail(mailDTO);
        }
    }

    public void sendAdminNotificationForOwnerAddRoom(int ownerId) {
        AccountDTO owner = accountService.findById(ownerId);
        NotificationDTO dto = new NotificationDTO();
        dto.setNavigate("/admin-home");
        dto.setMessage("Có một phòng trọ mới thêm cần xác nhận từ chủ trọ " + owner.getMail());
        for(AccountDTO admin : accountService.findAllAdmin()){
            dto.setReceiverId(admin.getId());
            notificationService.add(dto);
        }
    }

    public void sendOwnerMailForAdminConfirmAddRoom(){}
    public void sendOwnerNotificationForAdminConfirmAddRoom(){};

    public void sendAdminMailForOwnerAddMotel() {
    }

    public void sendAdminNotificationForOwnerAddMotel() {
    }

    String getString(String str){
        return str == null ? "" : str;
    }
}
