package com.shop_backend.initializers;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import com.shop_backend.models.Role;
import com.shop_backend.models.RoleName;
import com.shop_backend.models.AuthUser;
import com.shop_backend.repositories.RoleRepository;
import com.shop_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @PostConstruct
  public void init() {

    List<Role> roles = new ArrayList<>();
    for (RoleName roleName : RoleName.values()) {
      Role role = new Role();
      role.setName(roleName);
      roles.add(roleRepository.save(role));
    }

    AuthUser admin = new AuthUser();
    admin.setName("admin");
    admin.setUsername("admin");
    admin.setEmail("admin@gmail.com");
    admin.setPassword("admin");
    admin.setRoles(roles);
    userRepository.save(admin);
  }
}
