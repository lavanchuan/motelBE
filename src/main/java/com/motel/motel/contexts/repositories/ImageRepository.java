package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.ImageDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<ImageDAO, Integer> {
}
