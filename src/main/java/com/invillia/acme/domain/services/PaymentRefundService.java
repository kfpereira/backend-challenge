package com.invillia.acme.domain.services;

import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.model.Payment;
import com.invillia.acme.domain.observer.ObserverRefundOrder;
import com.invillia.acme.domain.repositories.PaymentRepository;
import com.invillia.acme.domain.types.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentRefundService implements ObserverRefundOrder {

    private final PaymentRepository repository;

    @Autowired
    public PaymentRefundService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Override
    public void update(OrderRefundService orderRefundService, Payment payment, OrderSale order) {
        update(payment, Status.REFUNDED);
    }

    private void update(Payment payment, Status status) {
        payment.setStatus(status);
        repository.saveAndFlush(payment);
    }
}
