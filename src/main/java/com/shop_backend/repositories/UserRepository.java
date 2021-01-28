package com.shop_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import com.shop_backend.models.AuthUser;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AuthUser, Long> {
  Optional<AuthUser> findByEmail(String email);

  Optional<AuthUser> findByUsernameOrEmail(String username, String email);

  List<AuthUser> findByIdIn(List<Long> userIds);

  Optional<AuthUser> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);
}
