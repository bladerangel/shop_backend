package com.shop_backend.controllers;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
  JwtTokenProvider tokenProvider;

  @PostMapping("/signin")
  public ResponseEntity<Object> authenticateUser(
      @Validated(value = { UserValidation.Login.class }) @RequestBody AuthUser user) throws ParseException {

    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    String jwt = tokenProvider.generateToken(authentication);

    Date expiryDate = tokenProvider.getExpiryDateFromJWT(jwt);

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

    Optional<AuthUser> userFound = userRepository.findByEmail(user.getEmail());
    Map<String, Object> response = new HashMap<>();
    response.put("user", userFound);
    response.put("token", jwt);
    response.put("expiryDate", formatter.format(expiryDate));

    return ResponseEntity.ok(response);
  }

  @PostMapping("/signup")
  public ResponseEntity<AuthUser> registerUser(
      @Validated(value = { UserValidation.Register.class }) @RequestBody AuthUser user) {
    user.setId(null);

    if (userRepository.existsByEmail(user.getEmail())) {
      return ResponseEntity.badRequest().build();
    }

    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("User Role not set."));

    user.setRoles(Collections.singletonList(userRole));

    URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/users/{username}")
        .buildAndExpand(user.getUsername()).toUri();

    try {
      return ResponseEntity.created(location).body(userRepository.save(user));
    } catch (Exception error) {
      return ResponseEntity.notFound().build();
    }
  }

  @GetMapping
  @PreAuthorize("hasRole('ADMIN')")
  public List<AuthUser> findAll() {
    return userRepository.findAll();
  }

}