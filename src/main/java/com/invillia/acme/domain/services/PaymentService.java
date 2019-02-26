package com.invillia.acme.domain.services;

import com.invillia.acme.config.errors.ErrorMessages;
import com.invillia.acme.config.exceptions.PaymentInvalidException;
import com.invillia.acme.domain.model.CreditCard;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.model.Payment;
import com.invillia.acme.domain.repositories.PaymentRepository;
import com.invillia.acme.domain.types.PaymentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    @Autowired
    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    public Payment save(OrderSale order, Date date, CreditCard creditCard) throws PaymentInvalidException {
        Payment payment = Payment.builder()
                .order(order)
                .paymentDate(date)
                .creditCard(creditCard)
                .status(PaymentStatus.CONCLUDED)
                .build();

        validate(payment);

        return repository.saveAndFlush(payment);
    }

    private void validate(Payment payment) throws PaymentInvalidException {
        if (payment.getCreditCard().getValidateDate().before(payment.getPaymentDate()))
            throw new PaymentInvalidException(ErrorMessages.CREDIT_CARD_INVALID.toString());
    }
}
