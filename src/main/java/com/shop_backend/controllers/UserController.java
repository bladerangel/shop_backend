package com.shop_backend.controllers;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import com.shop_backend.configurations.JwtTokenProvider;
import com.shop_backend.models.Role;
import com.shop_backend.models.RoleName;
import com.shop_backend.models.AuthUser;
import com.shop_backend.repositories.RoleRepository;
import com.shop_backend.repositories.UserRepository;
import com.shop_backend.validations.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/user")
public class UserController {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  JwtTokenProvider tokenProvider;

  @PostMapping("/signin")
  public ResponseEntity<String> authenticateUser(
      @Validated(value = { UserValidation.Login.class }) @RequestBody AuthUser user) {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);
    return ResponseEntity.ok(jwt);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(
      @Validated(value = { UserValidation.Register.class }) @RequestBody AuthUser user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      return ResponseEntity.badRequest().build();

    }

    if (userRepository.existsByEmail(user.getEmail())) {
      return ResponseEntity.badRequest().build();
    }

    user.setPassword(passwordEncoder.encode(user.getPassword()));

    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("User Role not set."));

    user.setRoles(Collections.singletonList(userRole));

    userRepository.save(user);

    URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
        .buildAndExpand(user.getUsername()).toUri();

    return ResponseEntity.created(location).body("User registered successfully");
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<AuthUser> findAll() {
    return userRepository.findAll();
  }

}