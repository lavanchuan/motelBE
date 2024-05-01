package com.motel.motel.services;

import com.motel.motel.models.dtos.*;
import com.motel.motel.models.e.BookRoomStatus;
import com.motel.motel.models.e.MakeAppointStatus;
import com.motel.motel.models.e.MotelStatus;
import com.motel.motel.models.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OwnerService {
    @Autowired
    AdminService adminService;

    public OtherResponse<List<MotelDTO>> addMotel(MotelDTO request) {
        switch (adminService.motelService.add(request).getStatus()) {
            case BaseResponse.ERROR:
                return new OtherResponse<>(BaseResponse.ERROR);
            default:
                List<MotelDTO> data = adminService.motelService.findAll().stream().filter(motel -> motel.getOwnerId() == request.getOwnerId())
                        .toList();

                return new OtherResponse<>(data);//TODO CHECK
        }
    }

    public OtherResponse<List<MotelDTO>> updateMotel(MotelDTO request) {
        switch (adminService.motelService.update(request).getStatus()) {
            case BaseResponse.ERROR:
                return new OtherResponse<>(BaseResponse.ERROR);
            default:
                List<MotelDTO> data = adminService.motelService.findAll().stream().filter(motel -> motel.getOwnerId() == request.getOwnerId())
                        .toList();

                return new OtherResponse<>(data);
        }
    }

    public OtherResponse<List<MotelRoomDTO>> addMotelRoom(MotelRoomDTO request) {
        MotelRoomResponse response = adminService.motelRoomService.add(request);
        switch (response.getStatus()) {
            case BaseResponse.ERROR:
                return new OtherResponse<>(BaseResponse.ERROR);
            default:

                MotelRoomDTO room = response.getData();
                MotelDTO motel_ = adminService.motelService.findById(room.getMotelId());
                AccountDTO owner = adminService.accountService.findById(motel_.getOwnerId());

                adminService.appSystemService.sendAdminMailForOwnerAddRoom(owner, room, motel_);
                adminService.appSystemService.sendAdminNotificationForOwnerAddRoom(motel_.getOwnerId());

                List<MotelRoomDTO> data = adminService.motelRoomService.findAll().stream().filter(motel -> motel.getMotelId() == request.getMotelId())
                        .toList();

                return new OtherResponse<>(data);
        }
    }

    public OtherResponse<List<MotelRoomDTO>> updateMotelRoom(MotelRoomDTO request) {
        switch (adminService.motelRoomService.update(request).getStatus()) {
            case BaseResponse.ERROR:
                return new OtherResponse<>(BaseResponse.ERROR);
            default:

//                adminService.motelRoomService.update(request);

                List<MotelRoomDTO> data = adminService.motelRoomService.findAll()
                        .stream().filter(motel -> motel.getMotelId() == request.getMotelId())
                        .toList();

                return new OtherResponse<>(data);
        }
    }

    public BookRoomResponse confirmBookRoomRequest(BookRoomDTO request, boolean isConfirm) {
        if (isConfirm) request.setStatus(BookRoomStatus.CONFIRMED);
        else {
            request.setStatus(BookRoomStatus.REJECTED);
        }
        BookRoomResponse response = adminService.bookRoomService.update(request);
        switch (response.getStatus()) {
            case BaseResponse.ERROR:
                return new BookRoomResponse(BaseResponse.ERROR);
            default:
                BookRoomDTO room = adminService.bookRoomService.findById(request.getId());
                adminService.appSystemService.sendUserMailForOwnerConfirmBookRoom(room, isConfirm);
                adminService.appSystemService.sendUserNotificationForOwnerConfirmBookRoom(room.getUserId(), isConfirm);
                return response;
        }
    }


    public OtherResponse<List<MakeAppointDTO>> confirmMakeAppoint(MakeAppointDTO request, boolean isConfirm) {
        if (isConfirm) request.setStatus(MakeAppointStatus.CONFIRMED);
        else request.setStatus(MakeAppointStatus.REJECTED);
        switch (adminService.makeAppointService.update(request).getStatus()) {
            case BaseResponse.ERROR:
                return new OtherResponse<>(BaseResponse.ERROR);

            default:

                MakeAppointDTO appoint = adminService.makeAppointService.findById(request.getId());
                adminService.appSystemService.sendUserMailForOwnerConfirmBookAppoint(appoint, isConfirm);
                adminService.appSystemService.sendUserNotificationForOwnerConfirmBookAppoint(appoint.getUserId(), isConfirm);

                return new OtherResponse<>(adminService.makeAppointService.findAll().stream()
                        .filter(makeAppoint -> makeAppoint.getMotelRoomId() == request.getMotelRoomId())
                        .toList());
        }
    }

    //TODO send message for user
    public OtherResponse<List<MessageDTO>> sendMessageUser(MessageDTO request) {
        switch (adminService.messageService.add(request).getStatus()) {
            case BaseResponse.ERROR:
                return new OtherResponse<>(BaseResponse.ERROR);
            default:
                return new OtherResponse<>(adminService.messageService.findAllBySenderReceiver(request.getSenderId(), request.getReceiverId()));
        }
    }

    //TODO send message for admin
    public OtherResponse<List<MessageDTO>> sendMessageAdmin(MessageDTO request) {

        List<AccountDTO> admins = adminService.accountService.findAllAdmin();
        admins.forEach(admin -> {
            request.setReceiverId(admin.getId());
            adminService.messageService.add(request);
        });

        return new OtherResponse<>(adminService.messageService.findAllBySenderReceiver(request.getSenderId(), admins.get(0).getId()));
    }

    //TODO list account by ownerId(sender)
    public OtherResponse<List<AccountDTO>> findAllReceiverByOwnerId(int ownerId) {
        return new OtherResponse<>(adminService.accountService.findAllBySenderId(ownerId));
    }

    //TODO all message of Owner and user/admin
    public OtherResponse<List<MessageDTO>> findAllMessageByOwnerIdReceiverId(int ownerId, int receiverId) {
        if (!adminService.accountService.existsById(ownerId) ||
                !adminService.accountService.existsById(receiverId)) return new OtherResponse<>(BaseResponse.ERROR);
        return new OtherResponse<>(adminService.messageService.findAllBySenderReceiver(ownerId, receiverId));
    }

    public List<MotelDTO> findAllMotelByOwner(int ownerId) {
        return adminService.motelService.findAll()
                .stream().filter(motel -> motel.getOwnerId() == ownerId)
                .toList();
    }


    public List<MotelRoomDTO> motelRoomFindAll(int motelId) {
        return adminService.motelRoomService.findAll()
                .stream().filter(room -> room.getMotelId() == motelId)
                .toList();
    }

    public List<MakeAppointDetail> findAllMakeAppoint(int ownerId) {
        List<MakeAppointDetail> result = new ArrayList<>();

        List<MakeAppointDTO> makeAppointDTOList = adminService.makeAppointService.findAllByOwnerId(ownerId);
        for (MakeAppointDTO makeAppoint : makeAppointDTOList) {
            MotelRoomDTO room = adminService.motelRoomService.findById(makeAppoint.getMotelRoomId());
            MotelDTO motel = adminService.motelService.findById(room.getMotelId());
            result.add(new MakeAppointDetail(makeAppoint, room, motel));
        }

        return result;
    }

    public ObjResponse.CountMotelActive countMotelActivate(int ownerId) {
        int countActivate = adminService.motelService.findAll().stream()
                .filter(motel -> motel.getOwnerId() == ownerId &&
                        motel.getStatus() == MotelStatus.ACTIVATING)
                .toList().size();

        int count = adminService.motelService.findAll().stream()
                .filter(motel -> motel.getOwnerId() == ownerId)
                .toList().size();

        if (count == 0) return new ObjResponse.CountMotelActive();

        return new ObjResponse.CountMotelActive(countActivate, count);
    }

    public List<ObjResponse.BookingDetail> findAllBookingByOwnerId(int ownerId) {
        List<ObjResponse.BookingDetail> response = new ArrayList<>();

        List<BookRoomDTO> bookings = adminService.bookRoomService.findAllBookingByOwnerId(ownerId);

        for(BookRoomDTO booking : bookings){
            ObjResponse.BookingDetail obj = new ObjResponse.BookingDetail();

            obj.setBooking(booking);

            obj.setUser(adminService.accountService.findById(booking.getUserId()));

            obj.setRoom(adminService.motelRoomService.findById(booking.getMotelRoomId()));

            obj.setMotel(adminService.motelService.findById(obj.getRoom().getMotelId()));

            response.add(obj);
        }

        return response;
    }

    public OtherResponse<?> addRoomImage(ImageDTO request) {
        return adminService.imageService.add(request);
    }
}
