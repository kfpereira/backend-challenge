package com.invillia.acme.domain.repositories;

import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.types.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderSaleRepository extends JpaRepository<OrderSale, Long> {

    List<OrderSale> findByConfirmationDateBetween(Date initial, Date end);

    List<OrderSale> findByStatus(Status status);
}
