package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.PasswordDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordDAO, Integer> {
}
