package com.shop_backend.repositories;

import java.util.Optional;
import com.shop_backend.models.Role;
import com.shop_backend.models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleName roleName);
}
