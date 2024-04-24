package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.NotificationDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationDAO, Integer> {
}
