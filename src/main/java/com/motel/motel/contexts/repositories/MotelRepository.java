package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.MotelDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MotelRepository extends JpaRepository<MotelDAO, Integer> {
}
