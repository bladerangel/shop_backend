package com.shop_backend.repositories;

import org.springframework.data.repository.CrudRepository;
import com.shop_backend.models.Product;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductRepository extends CrudRepository<Product, Long> {

}
