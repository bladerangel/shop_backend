package com.shop_backend.controllers;

import java.util.List;
import java.util.Optional;
import com.shop_backend.models.Product;
import com.shop_backend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  @GetMapping
  @PreAuthorize("hasRole('USER')")
  public List<Product> findAll() {
    return productRepository.findAll();
  }

  @GetMapping(value = "/{id}")
  @PreAuthorize("hasRole('USER')")
  public ResponseEntity<Product> findOne(@PathVariable(value = "id") long id) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isPresent()) {
      return ResponseEntity.ok().body(product.get());
    }
    return ResponseEntity.notFound().build();
  }

  @PostMapping
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Product> create(@Valid @RequestBody Product product) {
    try {
      product.setId(null);
      return ResponseEntity.ok().body(productRepository.save(product));
    } catch (Exception error) {
      return ResponseEntity.notFound().build();
    }
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Product> update(@PathVariable("id") Long id, @RequestBody @Valid Product product) {

    Optional<Product> foundProduct = productRepository.findById(id);
    if (foundProduct.isPresent()) {
      Product updatedProduct = foundProduct.get();
      updatedProduct = product;
      updatedProduct.setId(id);
      productRepository.save(updatedProduct);
      return ResponseEntity.ok().body(updatedProduct);
    }
    return ResponseEntity.notFound().build();

  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
    Optional<Product> product = productRepository.findById(id);
    if (product.isPresent()) {
      productRepository.delete(product.get());
      return ResponseEntity.ok().build();
    }
    return ResponseEntity.notFound().build();

  }
}
