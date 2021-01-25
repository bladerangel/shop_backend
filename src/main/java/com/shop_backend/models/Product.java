package com.shop_backend.models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.URL;
import lombok.Data;

@Entity
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotEmpty()
  private String title;

  @NotEmpty()
  @Size(min = 10)
  private String description;

  @DecimalMin(value = "0.0")
  private Double price;

  @NotEmpty()
  @URL()
  private String imageUrl;

  @NotNull()
  private Boolean isFavorite;

  @JsonIgnore
  @OneToMany(mappedBy = "product")
  private List<CartItem> cartItems;

}