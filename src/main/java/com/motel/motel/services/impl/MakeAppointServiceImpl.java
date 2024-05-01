package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.MakeAppointDTO;
import com.motel.motel.models.e.MakeAppointStatus;
import com.motel.motel.models.entities.MakeAppointDAO;
import com.motel.motel.models.mapper.AccountMapper;
import com.motel.motel.models.mapper.MakeAppointMapper;
import com.motel.motel.models.mapper.MotelMapper;
import com.motel.motel.models.mapper.MotelRoomMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.ObjResponse;
import com.motel.motel.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MakeAppointServiceImpl implements ICRUDService<MakeAppointDTO, Integer, BaseResponse<MakeAppointDTO>> {

    @Autowired
    DbContext dbContext;

    @Autowired
    MakeAppointMapper makeAppointMapper;

    //OTHER MAPPER
    @Autowired
    AccountMapper accountMapper;
    @Autowired
    MotelRoomMapper roomMapper;
    @Autowired
    MotelMapper motelMapper;

    @Override
    public BaseResponse<MakeAppointDTO> add(MakeAppointDTO makeAppointDTO) {

        if (dbContext.makeAppointRepository.existsById(makeAppointDTO.getId()))
            return new MakeAppointResponse(BaseResponse.ERROR);

        return new MakeAppointResponse(makeAppointMapper.toDTO(dbContext
                .makeAppointRepository.save(makeAppointMapper
                        .toDAO(makeAppointDTO, dbContext))));
    }

    @Override
    public BaseResponse<MakeAppointDTO> update(MakeAppointDTO makeAppointDTO) {
        if (!dbContext.makeAppointRepository.existsById(makeAppointDTO.getId()))
            return new MakeAppointResponse(BaseResponse.ERROR);

        return new MakeAppointResponse(makeAppointMapper.toDTO(dbContext
                .makeAppointRepository.save(makeAppointMapper
                        .toDAO(makeAppointDTO, dbContext))));
    }

    @Override
    public BaseResponse<MakeAppointDTO> delete(Integer integer) {
        return null;
    }

    @Override
    public List<MakeAppointDTO> findAll() {
        return dbContext.makeAppointRepository.findAll()
                .stream().map(makeAppointMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MakeAppointDTO findById(Integer id) {
        if (dbContext.makeAppointRepository.existsById(id))
            return makeAppointMapper.toDTO(dbContext.makeAppointRepository.findById(id).orElseThrow());
        return null;
    }

    public List<MakeAppointDTO> findAllByOwnerId(int ownerId) {
        return dbContext.makeAppointRepository.findAllByOwnerId(ownerId)
                .stream().map(makeAppointMapper::toDTO)
                .toList();
    }

    public List<?> findAllByUserId(int userId) {
        List<ObjResponse.AppointDetail> response = new ArrayList<>();

        List<MakeAppointDTO> appoints = findAll().stream()
                .filter(appoint -> appoint.getUserId() == userId)
                .toList();

        for (MakeAppointDTO element : appoints) {
            ObjResponse.AppointDetail obj = new ObjResponse.AppointDetail();
            obj.setAppoint(element);

//            obj.setUser(accountMapper.toDTO(dbContext.accountRepository.findById(element.getUserId()).orElseThrow()));

            obj.setRoom(roomMapper.toDTO(dbContext.motelRoomRepository.findById(element.getMotelRoomId()).orElseThrow()));

            obj.setMotel(motelMapper.toDTO(dbContext.motelRepository
                    .findById(obj.getRoom().getMotelId()).orElseThrow()));

            obj.setOwner(accountMapper.toDTO(dbContext.accountRepository
                    .findById(obj.getMotel().getOwnerId()).orElseThrow()));

            response.add(obj);
        }

        return response;
    }

    public List<?> cancelAppoint(int appointId) {
        if(!dbContext.makeAppointRepository.existsById(appointId)) return null;

        MakeAppointDTO appointDTO = makeAppointMapper.toDTO(dbContext.makeAppointRepository
                .findById(appointId).orElseThrow());

        appointDTO.setStatus(MakeAppointStatus.CANCELLED);
        dbContext.makeAppointRepository.save(makeAppointMapper.toDAO(appointDTO, dbContext));

        return findAllByUserId(appointDTO.getUserId());
    }

    // CLASS
    static class MakeAppointResponse extends BaseResponse<MakeAppointDTO> {

        public MakeAppointResponse(int status) {
            super(status);
        }

        public MakeAppointResponse(MakeAppointDTO makeAppointDTO) {
            super(makeAppointDTO);
        }
    }
}
