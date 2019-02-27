package com.invillia.acme.domain.services;

import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.model.Payment;
import com.invillia.acme.domain.observer.ObserverRefundOrder;
import com.invillia.acme.domain.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService implements ObserverRefundOrder  {

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

    public void refund(OrderItem item) {
        remove(item);
    }

    private void remove(OrderItem item) {
        repository.delete(item);
    }

    @Override
    public void update(OrderRefundService orderRefundService, Payment payment, OrderSale order) {
        find(order).forEach(this::refund);
    }
}
