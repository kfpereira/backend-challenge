package com.invillia.acme.domain.repositories;

import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.domain.model.OrderSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrder(OrderSale orderSale);
}
