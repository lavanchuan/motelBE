package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.AccountDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountDAO, Integer> {

    boolean existsByMail(String mail);

    AccountDAO findByMail(String mail);

    @Query(value = "select count(account.id) from account \n" +
            "inner join password on account.id = password.accountId \n" +
            "where account.mail = :mail \n" +
            "and password.pass = :password", nativeQuery = true)
    int countAccountValid(@Param(value = "mail") String mail, @Param("password") String password);

    @Query(value = "select account.* from account \n" +
            "inner join motel on motel.ownerId = account.id \n" +
            "inner join motelRoom on motelRoom.motelId = motel.id \n" +
            "where motelRoom.id = :roomId", nativeQuery = true)
    AccountDAO findByRoomId(@Param(value = "roomId") int roomId);
}
