package com.shop_backend.configurations;

import javax.transaction.Transactional;

import com.shop_backend.models.AuthUser;
import com.shop_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {

    AuthUser user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with username or email : " + usernameOrEmail));

    return user;
  }

  @Transactional
  public UserDetails loadUserById(Long id) {
    AuthUser user = userRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with id : " + id));

    return user;
  }
}
