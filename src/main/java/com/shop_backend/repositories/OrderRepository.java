package com.shop_backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.shop_backend.models.PurchaseOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<PurchaseOrder, Long> {

}
