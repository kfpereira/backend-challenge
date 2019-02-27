package com.invillia.acme.domain.services;

import com.invillia.acme.config.errors.ErrorMessages;
import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.model.Payment;
import com.invillia.acme.domain.observer.ObserverRefundOrder;
import com.invillia.acme.domain.repositories.OrderSaleRepository;
import com.invillia.acme.domain.types.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.invillia.acme.domain.utils.DateUtils.toDate;
import static com.invillia.acme.domain.utils.DateUtils.toLocalDate;


@Service
public class OrderRefundService {

    private final OrderSaleRepository repository;
    private final Clock clock;
    private PaymentService paymentService;
    private OrderItemService itemService;

    private static final int DAYS_OF_REFUND_UNTIL = 10;

    private List<ObserverRefundOrder> observers = new ArrayList<>();

    @Autowired
    public OrderRefundService(OrderSaleRepository repository,
                              Clock clock,
                              PaymentService paymentService,
                              OrderItemService itemService) {
        this.repository = repository;
        this.clock = clock;
        this.paymentService = paymentService;
        this.itemService = itemService;
    }

    public void refund(OrderSale order) {
        validateRefund(order);

        this.notifyObservers(paymentService.find(order), order);

        order.setStatus(Status.REFUNDED);
        order.setValue(BigDecimal.ZERO);
        repository.saveAndFlush(order);
    }

    private void validateRefund(OrderSale order) {
        if (!order.getStatus().equals(Status.CONCLUDED))
            throw new IllegalArgumentException(ErrorMessages.PAYMENT_NOT_CONCLUDED.toString());

        LocalDate dateConfirmation = toLocalDate(order.getConfirmationDate());
        Date dateAccepted = toDate(dateConfirmation.plusDays(DAYS_OF_REFUND_UNTIL));
        Date currentDate = toDate(LocalDate.now(clock));
        if (currentDate.after(dateAccepted))
            throw new IllegalArgumentException(ErrorMessages.REFUNDED_DATE_INVALID.toString());
    }

    public void subscribe(ObserverRefundOrder observer) {
        observers.add(observer);
    }

    public void unsubscribe(ObserverRefundOrder observer) {
        observers.remove(observer);
    }

    private void notifyObservers(Payment payment, OrderSale order) {
        observers.forEach((ObserverRefundOrder observer) -> observer.update(this, payment, order));
    }

    @Transactional
    public void refund(OrderItem item) {
        OrderSale order = item.getOrder();
        validateRefund(order);

        update(item, order);

        itemService.refund(item);
        refundWhenItemsAreEmpty(order);
    }

    private void refundWhenItemsAreEmpty(OrderSale order) {
        if (itemService.find(order).isEmpty())
            refund(order);
    }

    private void update(OrderItem item, OrderSale order) {
        order.setValue(order.getValue().subtract(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity()))));
        repository.save(order);
    }
}
