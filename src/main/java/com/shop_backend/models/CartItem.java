package com.shop_backend.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Entity
@Data
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
  private Product product;

  @Min(1)
  private Integer quantity;

  @ManyToOne(optional = false)
  @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
  @JsonBackReference
  private Cart cart;

}
