package com.motel.motel.services.impl;

import com.motel.motel.contexts.DbContext;
import com.motel.motel.models.dtos.NotificationDTO;
import com.motel.motel.models.e.NotificationStatus;
import com.motel.motel.models.mapper.NotificationMapper;
import com.motel.motel.models.response.BaseResponse;
import com.motel.motel.models.response.ObjResponse;
import com.motel.motel.models.response.OtherResponse;
import com.motel.motel.services.ICRUDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements ICRUDService<NotificationDTO, Integer, OtherResponse<NotificationDTO>> {
    @Autowired
    DbContext dbContext;

    @Autowired
    NotificationMapper notificationMapper;

    @Override
    public OtherResponse<NotificationDTO> add(NotificationDTO notificationDTO) {
        if (findById(notificationDTO.getId()) != null) return new OtherResponse<>(BaseResponse.ERROR);
        return new OtherResponse<>(notificationMapper.toDTO(dbContext
                .notificationRepository.save(notificationMapper
                        .toDAO(notificationDTO, dbContext))));
    }

    @Override
    public OtherResponse<NotificationDTO> update(NotificationDTO notificationDTO) {
        if (findById(notificationDTO.getId()) == null) return new OtherResponse<>(BaseResponse.ERROR);
        return new OtherResponse<>(notificationMapper.toDTO(dbContext
                .notificationRepository.save(notificationMapper
                        .toDAO(notificationDTO, dbContext))));
    }

    @Override
    public OtherResponse<NotificationDTO> delete(Integer integer) {
        return null;
    }

    @Override
    public List<NotificationDTO> findAll() {
        return dbContext.notificationRepository.findAll()
                .stream().map(notificationMapper::toDTO)
                .toList();
    }

    @Override
    public NotificationDTO findById(Integer id) {
        if (!dbContext.notificationRepository.existsById(id)) return null;
        return notificationMapper.toDTO(dbContext.notificationRepository.findById(id).orElseThrow());
    }

    public ObjResponse.BaseCount countNotificationReceived(int receiverId) {
        int count = findAll().stream()
                .filter(notification -> notification.getReceiverId() == receiverId &&
                        notification.getStatus() == NotificationStatus.SENT)
                .toList().size();

        return new ObjResponse.BaseCount(count);
    }

    public List<?> findAllByReceiverId(int receiverId) {
        return findAll().stream()
                .filter(notification -> notification.getReceiverId() == receiverId)
                .toList();
    }
}
