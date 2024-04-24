package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.MakeAppointDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface MakeAppointRepository extends JpaRepository<MakeAppointDAO, Integer> {

    @Query(value = "select distinct makeAppoint.* from makeAppoint \n" +
            "inner join motelRoom on motelRoom.id = makeAppoint.motelRoomId \n" +
            "inner join motel on motel.id = motelRoom.motelId \n" +
            "where motel.ownerId = :ownerId \n" +
            "order by makeAppoint.createAt desc", nativeQuery = true)
    List<MakeAppointDAO> findAllByOwnerId(@Param(value = "ownerId") int ownerId);
}
