package com.invillia.acme.domain.observer;

import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.model.Payment;
import com.invillia.acme.domain.services.OrderRefundService;

public interface ObserverRefundOrder {

    void update(OrderRefundService orderRefundService, Payment payment, OrderSale order);

}
