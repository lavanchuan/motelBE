package com.motel.motel.models.entities;

import com.motel.motel.models.e.RoleName;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDAO extends BaseEntity{

    @Enumerated(EnumType.STRING)
    private RoleName name;
}
