package com.shop_backend.models;

import javax.persistence.*;

@Entity
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  private String title;

  private String description;

  private double price;

  private String imageUrl;

  private boolean isFavorite;
}