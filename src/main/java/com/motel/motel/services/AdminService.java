package com.motel.motel.services;

import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.MotelDTO;
import com.motel.motel.models.dtos.MotelRoomDTO;
import com.motel.motel.models.e.AccountStatus;
import com.motel.motel.models.e.MotelStatus;
import com.motel.motel.models.e.RoleName;
import com.motel.motel.models.e.RoomStatus;
import com.motel.motel.models.request.ConfirmRequest;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.OtherResponse;
import com.motel.motel.models.response.RoomOwnerResponse;
import com.motel.motel.services.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Autowired
    public MotelServiceImpl motelService;

    @Autowired
    public AppSystemService appSystemService;

    @Autowired
    public ImageServiceImpl imageService;

    @Autowired
    public NotificationServiceImpl notificationService;

    //TODO confirm regis owner request
    public OtherResponse<String> confirmRegisOwnerRequest(int userId, boolean isConfirm) {
        if (!accountService.existsById(userId)) return new OtherResponse<>(BaseResponse.ERROR);
        AccountDTO dto = accountService.findById(userId);
        if (isConfirm) {
            dto.setStatus(AccountStatus.CONFIRMED_OWNER_REGIS);
            dto.setRole(RoleName.OWNER);
        }
        else dto.setStatus(AccountStatus.REJECTED_OWNER_REGIS);
        accountService.update(dto);

        //TODO ??

        return new OtherResponse<>("Confirmed regis owner request");
    }

    //TODO confirm add motel of owner
    public OtherResponse<String> confirmAddMotel(int motelId, boolean confirm) {
        if (!motelService.existsById(motelId)) return new OtherResponse<>(BaseResponse.ERROR);
        MotelDTO dto = motelService.findById(motelId);
        if (confirm) dto.setStatus(MotelStatus.CONFIRMED_CREATE_REQUEST);
        else dto.setStatus(MotelStatus.REJECT_CREATE_REQUEST);
        motelService.update(dto);
        return new OtherResponse<>("Confirmed create motel request");
    }

    //TODO confirm add room request
    public OtherResponse<String> confirmCreateRoomRequest(ConfirmRequest request, int adminId, boolean isConfirm) {
        if (!motelRoomService.existsById(request.getId())) return new OtherResponse<>(BaseResponse.ERROR);
        MotelRoomDTO dto = motelRoomService.findById(request.getId());
        if (isConfirm) dto.setStatus(RoomStatus.CONFIRMED_CREATE_REQUEST);
        else dto.setStatus(RoomStatus.REJECT_CREATE_REQUEST);

        dto.setAdminId(adminId);

        MotelRoomDTO room = motelRoomService.update(dto).getData();

        //TODO send mail and notification

        return new OtherResponse<>("Confirmed create room request");
    }


    public List<RoomOwnerResponse> findAllRoomAdd() {
        return motelRoomService.findAllInfo()
                .stream().filter(roomInfo -> roomInfo.getData().getRoom().getStatus() == RoomStatus.PROCESSING_CREATE)
                .toList();
    }
}
