package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.BookRoomDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRoomRepository extends JpaRepository<BookRoomDAO, Integer> {
}
