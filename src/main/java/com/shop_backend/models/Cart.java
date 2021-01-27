package com.shop_backend.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

@Entity
@Data
public class Cart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL)
  @JsonManagedReference
  List<CartItem> cartItems;

  @JsonIgnore
  @OneToOne(mappedBy = "cart", optional = false)
  private PurchaseOrder purchaseOrder;

}
