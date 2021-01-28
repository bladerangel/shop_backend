package com.shop_backend.controllers;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import com.shop_backend.models.PurchaseOrder;
import com.shop_backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

  @Autowired
  private OrderRepository orderRepository;

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public List<PurchaseOrder> findAll() {
    return orderRepository.findAll();
  }

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<PurchaseOrder> findOne(@PathVariable(value = "id") long id) {

    Optional<PurchaseOrder> product = orderRepository.findById(id);
    if (product.isPresent()) {
      return ResponseEntity.ok().body(product.get());
    }
    return ResponseEntity.notFound().build();

  }

  @PostMapping
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<PurchaseOrder> create(@Valid @RequestBody PurchaseOrder order) {
    try {
      order.setId(null);
      return ResponseEntity.ok().body(orderRepository.save(order));
    } catch (Exception error) {
      System.out.println(error.getMessage());
      return ResponseEntity.notFound().build();
    }

  }

}
