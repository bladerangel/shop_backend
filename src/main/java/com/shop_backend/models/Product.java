package com.shop_backend.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.URL;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  private String title;

  @NotBlank
  @Size(min = 10)
  private String description;

  @DecimalMin(value = "0.0")
  private Double price;

  @NotBlank
  @URL
  private String imageUrl;

  @JsonIgnore
  @OneToMany(mappedBy = "product")
  private List<CartItem> cartItems;

  @JsonIgnore
  @ManyToMany(mappedBy = "favoriteProducts")
  private List<AuthUser> authUser;

}