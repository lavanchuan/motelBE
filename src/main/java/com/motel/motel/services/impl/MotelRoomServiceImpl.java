package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.AccountDTO;
import com.motel.motel.models.dtos.MotelDTO;
import com.motel.motel.models.dtos.MotelRoomDTO;
import com.motel.motel.models.entities.MotelRoomDAO;
import com.motel.motel.models.mapper.AccountMapper;
import com.motel.motel.models.mapper.MotelMapper;
import com.motel.motel.models.mapper.MotelRoomMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.MotelRoomResponse;
import com.motel.motel.models.response.RoomOwnerResponse;
import com.motel.motel.services.ICRUDService;
import com.motel.motel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MotelRoomServiceImpl implements ICRUDService<MotelRoomDTO, Integer, MotelRoomResponse> {

    @Autowired
    DbContext dbContext;

    @Autowired
    MotelRoomMapper motelRoomMapper;

    @Autowired
    MotelMapper motelMapper;

    @Override
    public MotelRoomResponse add(MotelRoomDTO dto) {
        if(dbContext.motelRoomRepository.existsById(dto.getId()))
            return new MotelRoomResponse(BaseResponse.CONFLICT);

        MotelRoomDAO dao = dbContext.motelRoomRepository.save(motelRoomMapper.toDAO(dto, dbContext));

        return new MotelRoomResponse(findById(dao.getId()));
    }

    @Override
    public MotelRoomResponse update(MotelRoomDTO motelRoomDTO) {
        return null;
    }

    @Override
    public MotelRoomResponse delete(Integer integer) {
        return null;
    }

    @Override
    public List<MotelRoomDTO> findAll() {
        return dbContext.motelRoomRepository
                .findAll().stream().map(motelRoomMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Autowired
    AccountMapper accountMapper;
    public List<RoomOwnerResponse> findAllInfo() {
        List<MotelRoomDTO> roomList = dbContext.motelRoomRepository.findAll()
                .stream().map(motelRoomMapper::toDTO)
                .toList();

        List<RoomOwnerResponse> result = new ArrayList<>();
        for(MotelRoomDTO room : roomList){
            AccountDTO owner = accountMapper.toDTO(dbContext.accountRepository.findByRoomId(room.getId()));
            UserService.RoomOwnerResponse roomOwner = new UserService.RoomOwnerResponse();
            MotelDTO motel = motelMapper.toDTO(dbContext.motelRepository.findById(room.getMotelId()).orElseThrow());
            roomOwner.setOwner(owner);
            roomOwner.setRoom(room);
            roomOwner.setMotel(motel);

            result.add(new RoomOwnerResponse(roomOwner));
        }

        return result;
    }

    @Override
    public MotelRoomDTO findById(Integer id) {
        if(!dbContext.motelRoomRepository.existsById(id))
            return null;
        return motelRoomMapper.toDTO(dbContext.motelRoomRepository.findById(id).orElseThrow());
    }

    public boolean existsById(int roomId) {
        return dbContext.motelRoomRepository.existsById(roomId);
    }

    public boolean isOneSelf(int userId, int motelRoomId) {
        return dbContext.motelRoomRepository.isOneSelf(userId, motelRoomId) == 1;
    }
}
