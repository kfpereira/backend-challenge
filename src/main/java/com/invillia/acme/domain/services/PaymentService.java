package com.invillia.acme.domain.services;

import com.invillia.acme.config.errors.ErrorMessages;
import com.invillia.acme.config.exceptions.PaymentInvalidException;
import com.invillia.acme.domain.model.CreditCard;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.model.Payment;
import com.invillia.acme.domain.observer.ObserverPayment;
import com.invillia.acme.domain.repositories.PaymentRepository;
import com.invillia.acme.domain.types.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.invillia.acme.domain.utils.DateUtils.toDate;

@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final Clock clock;

    private List<ObserverPayment> observers = new ArrayList<>();

    @Autowired
    public PaymentService(PaymentRepository repository, Clock clock) {
        this.repository = repository;
        this.clock = clock;
    }

    @Transactional
    public Payment save(OrderSale order, CreditCard creditCard) throws PaymentInvalidException {
        Payment payment = Payment.builder()
                .order(order)
                .paymentDate(toDate(LocalDate.now(clock)))
                .creditCard(creditCard)
                .status(Status.CONCLUDED)
                .build();

        validate(payment);

        this.notifyObservers(order);

        return repository.saveAndFlush(payment);
    }

    private void validate(Payment payment) throws PaymentInvalidException {
        if (payment.getCreditCard().getValidateDate().before(payment.getPaymentDate()))
            throw new PaymentInvalidException(ErrorMessages.CREDIT_CARD_INVALID.toString());
    }

    public void subscribe(ObserverPayment observer) {
        observers.add(observer);
    }

    public void unsubscribe(ObserverPayment observer) {
        observers.remove(observer);
    }

    private void notifyObservers(OrderSale order) {
        observers.forEach((ObserverPayment observer) -> observer.update(this, order));
    }

    public Payment find(OrderSale order) {
        return repository.findByOrder(order);
    }
}
