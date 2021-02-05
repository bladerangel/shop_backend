package com.shop_backend.controllers;

import java.util.List;
import javax.validation.Valid;

import com.shop_backend.models.AuthUser;
import com.shop_backend.models.PurchaseOrder;
import com.shop_backend.repositories.OrderRepository;
import com.shop_backend.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

  @Autowired
  private UserRepository userRepository;

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<List<PurchaseOrder>> orders(@AuthenticationPrincipal AuthUser authUser) {
    AuthUser user = userRepository.getOne(authUser.getId());
    List<PurchaseOrder> favoriteProducts = user.getPurchaseOrders();
    return ResponseEntity.ok().body(favoriteProducts);

  }

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<PurchaseOrder> create(@AuthenticationPrincipal AuthUser user,
      @Valid @RequestBody PurchaseOrder order) {
    try {
      order.setId(null);
      order.setAuthUser(user);
      return ResponseEntity.ok().body(orderRepository.save(order));
    } catch (Exception error) {
      return ResponseEntity.notFound().build();
    }
  }

}
