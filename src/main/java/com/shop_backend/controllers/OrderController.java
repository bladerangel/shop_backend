package com.shop_backend.controllers;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import com.shop_backend.configurations.JwtTokenProvider;
import com.shop_backend.models.AuthUser;
import com.shop_backend.models.PurchaseOrder;
import com.shop_backend.repositories.OrderRepository;
import com.shop_backend.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtTokenProvider tokenProvider;

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<PurchaseOrder>> orders(@RequestHeader(name = "Authorization") String token) {

    Long id = tokenProvider.getUserIdFromJWT(tokenProvider.getBearerToken(token));
    Optional<AuthUser> foundUser = userRepository.findById(id);
    if (foundUser.isPresent()) {
      List<PurchaseOrder> favoriteProducts = foundUser.get().getPurchaseOrders();

      return ResponseEntity.ok().body(favoriteProducts);
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<PurchaseOrder> create(@RequestHeader(name = "Authorization") String token,
      @Valid @RequestBody PurchaseOrder order) {
    try {
      Long id = tokenProvider.getUserIdFromJWT(tokenProvider.getBearerToken(token));
      Optional<AuthUser> foundUser = userRepository.findById(id);
      if (foundUser.isPresent()) {
        order.setId(null);
        order.setAuthUser(foundUser.get());
        return ResponseEntity.ok().body(orderRepository.save(order));
      }
      return ResponseEntity.notFound().build();
    } catch (Exception error) {
      return ResponseEntity.notFound().build();
    }

  }

}
