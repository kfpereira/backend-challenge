package com.invillia.acme.domain.repositories;

import com.invillia.acme.domain.model.OrderSale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderSale, Long> {

}
