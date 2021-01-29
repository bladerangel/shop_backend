package com.shop_backend.listeners;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.shop_backend.models.AuthUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AuthUserListener {

  @Autowired
  PasswordEncoder passwordEncoder;

  @PrePersist
  @PreUpdate
  public void encodePassword(AuthUser user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));
  }

}