package com.motel.motel.contexts.repositories;

import com.motel.motel.models.entities.PasswordDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordRepository extends JpaRepository<PasswordDAO, Integer> {

    @Query(value = "select password.pass from password \n" +
            "inner join account on account.id = password.accountId \n" +
            "where account.mail = :email \n" +
            "limit 1", nativeQuery = true)
    String getPassword(@Param(value = "email") String email);

    @Query(value = "select password.* from password \n" +
            "where password.accountId = :userId \n" +
            "limit 1", nativeQuery = true)
    PasswordDAO findByUserId(@Param(value = "userId") int userId);
}
