package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.MessageDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageDAO, Integer> {

    @Query(value = "select message.* from message \n" +
            "where message.senderId = :senderId and message.receiverId = :receiverId \n" +
            "or message.senderId = :receiverId and message.receiverId = :senderId\n" +
            "order by message.createAt desc", nativeQuery = true)
    List<MessageDAO> findAllBySenderIdReceiverId(@Param(value = "senderId") int senderId,
                                                 @Param(value = "receiverId") int receiverId);

    @Query(value = "select distinct message.receiverId from message \n" +
            "where senderId = :senderId", nativeQuery = true)
    List<Integer> findAllReceiveIdBySenderId(@Param(value = "senderId") int senderId);

    @Query(value = "select message.* from message \n" +
            "where receiverId = :receiverId", nativeQuery = true)
    List<MessageDAO> findAllReceived(@Param(value = "receiverId") int receiverId);
}
