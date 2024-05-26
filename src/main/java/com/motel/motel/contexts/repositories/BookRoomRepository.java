package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.BookRoomDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface BookRoomRepository extends JpaRepository<BookRoomDAO, Integer> {

    @Query(value = "select distinct bookRoom.* from bookRoom \n" +
            "inner join motelRoom on motelRoom.id = bookRoom.motelRoomId \n" +
            "inner join motel on motel.id = motelRoom.motelId \n" +
            "where motel.ownerId = :ownerId \n" +
            "order by bookRoom.createAt desc", nativeQuery = true)
    List<BookRoomDAO> findAllBookingByOwnerId(@Param(value = "ownerId") int ownerId);

    @Query(value = "select bookRoom.* from bookRoom \n" +
            "where motelRoomId = :roomId \n" +
            "and userId = :userId", nativeQuery = true)
    List<BookRoomDAO> findAllByUserIdRoomId(@Param(value = "userId") int userId,
                                            @Param(value = "roomId") int roomId);

    @Query(value = "select * from bookRoom \n" +
            "where motelRoomId = :roomId \n" +
            "and userId = :userId \n" +
            "order by createAt desc " +
            "limit 1", nativeQuery = true)
    BookRoomDAO lastBookingRoom(@Param(value = "userId") int userId,
                                @Param(value = "roomId") int roomId);
}
