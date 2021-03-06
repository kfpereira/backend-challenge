package com.invillia.acme.domain.observer;

import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.services.PaymentService;

public interface ObserverPayment {

    void update(PaymentService subject, OrderSale order);
}
