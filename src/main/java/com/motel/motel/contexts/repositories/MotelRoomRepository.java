package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.MotelRoomDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotelRoomRepository extends JpaRepository<MotelRoomDAO, Integer> {
}
