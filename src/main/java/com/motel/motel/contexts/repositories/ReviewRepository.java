package com.motel.motel.contexts.repositories;

import com.motel.motel.models.dtos.ReviewDTO;
import com.motel.motel.models.entities.ReviewDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewDAO, Integer> {

    @Query(value = "select distinct review.* from review \n" +
            "inner join bookRoom on bookRoom.id = review.bookRoomId \n" +
            "where bookRoom.motelRoomId = :motelRoomId", nativeQuery = true)
    List<ReviewDAO> findAllByRoomId(@Param(value = "motelRoomId") int motelRoomId);
}
