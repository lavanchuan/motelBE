package com.motel.motel.contexts.repositories;

import com.motel.motel.models.e.RoleName;
import com.motel.motel.models.entities.RoleDAO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleDAO, Integer> {

    RoleDAO findByName(RoleName roleName);
}
