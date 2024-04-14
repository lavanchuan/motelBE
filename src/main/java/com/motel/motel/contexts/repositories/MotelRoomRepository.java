package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.MotelRoomDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MotelRoomRepository extends JpaRepository<MotelRoomDAO, Integer> {

    @Query(value = "select count(distinct motelRoom.id) > 0 from motelRoom \n" +
            "inner join motel on motel.id = motelRoom.motelId \n" +
            "where motel.ownerId = :userId \n" +
            "and motelRoom.id = :motelRoomId", nativeQuery = true)
    int isOneSelf(@Param(value = "userId") int userId,
                  @Param(value = "motelRoomId") int motelRoomId);
}
