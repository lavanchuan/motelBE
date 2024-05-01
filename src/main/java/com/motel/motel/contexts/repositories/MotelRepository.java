package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.MotelDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public interface MotelRepository extends JpaRepository<MotelDAO, Integer> {

    @Query(value = "select distinct motel.* from motel \n" +
            "order by motel.status asc, motel.ownerId asc", nativeQuery = true)
    List<MotelDAO> findAllForAdmin();
}
