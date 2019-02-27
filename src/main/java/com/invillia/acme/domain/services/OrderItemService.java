package com.invillia.acme.domain.services;

import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService {

    private final OrderItemRepository repository;

    @Autowired
    public OrderItemService(OrderItemRepository repository) {
        this.repository = repository;
    }

    public void save(OrderSale orderSale, List<OrderItem> itens) {
        itens.forEach(item -> item.setOrder(orderSale));
        repository.saveAll(itens);
    }

    public List<OrderItem> find(OrderSale orderSale) {
        return repository.findByOrder(orderSale);
    }
}
