package com.shop_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop_backend.models.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
