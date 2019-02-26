package com.invillia.acme.environments;

import com.invillia.acme.config.exceptions.*;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.repositories.OrderRepository;
import com.invillia.acme.domain.services.CreditCardService;
import com.invillia.acme.domain.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.invillia.acme.domain.utils.DateUtils.getDate;

@Component
public class EnvPayment {

    @Autowired
    private PaymentService service;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private EnvOrder envOrder;

    @Autowired
    private EnvCreditCard envCreditCard;

    @Autowired
    private CreditCardService creditCardService;

    public void init() throws Exception {
        OrderSale order = getOrderSale();

        service.save(order,
                getDate("01/02/2019"),
                creditCardService.find("52998224725", "1234567890123456", "123"));
    }

    public OrderSale getOrderSale() throws RecordFoundException, RecordNotFoundException, EmptyDataException, CnpjCpfInvalidException, CreditCardInvalidException {
        envOrder.init();
        envCreditCard.init();
        return orderRepository.findAll().get(0);
    }
}
