package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.MakeAppointDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeAppointRepository extends JpaRepository<MakeAppointDAO, Integer> {
}
