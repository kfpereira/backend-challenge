package com.invillia.acme;

import com.invillia.acme.config.exceptions.PaymentInvalidException;
import com.invillia.acme.core.FunctionalTest;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.model.Payment;
import com.invillia.acme.domain.repositories.PaymentRepository;
import com.invillia.acme.domain.services.CreditCardService;
import com.invillia.acme.domain.services.PaymentService;
import com.invillia.acme.domain.types.Status;
import com.invillia.acme.environments.AbstractIntegrationTest;
import com.invillia.acme.environments.EnvPayment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@FunctionalTest
class PaymentTest extends AbstractIntegrationTest {

    @Autowired
    private EnvPayment envPayment;

    @Autowired
    private PaymentService service;

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private CreditCardService creditCardService;

    @Test
    void happyDay() throws Exception {
        envPayment.init();
        assertEquals(1, repository.findAll().size());

        Payment payment = repository.findAll().get(0);
        assertEquals(Status.CONCLUDED, payment.getOrder().getStatus());
    }

    @Test
    void shouldNotSavePayment() throws Exception {
        OrderSale order = envPayment.getOrderSale();
        setDate(2019, Month.FEBRUARY, 28);
        assertThrows(PaymentInvalidException.class,
                () -> service.save(order,
                        creditCardService.find("52998224725", "1234567890123456", "123")
                ),
                "Invalid Credit Card"
        );
    }

}
