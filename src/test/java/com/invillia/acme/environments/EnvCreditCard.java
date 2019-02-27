package com.invillia.acme.environments;

import com.invillia.acme.config.exceptions.CnpjCpfInvalidException;
import com.invillia.acme.config.exceptions.CreditCardInvalidException;
import com.invillia.acme.domain.services.CreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static com.invillia.acme.domain.utils.DateUtils.getDate;
import static java.time.Month.FEBRUARY;

@Component
public class EnvCreditCard {

    private static final int DAY_ONE = 01;

    @Autowired
    private EnvMockClock mockClock;

    @Autowired
    private CreditCardService service;

    public void init() throws CnpjCpfInvalidException, CreditCardInvalidException {
        mockClock.setDate(2019, FEBRUARY, DAY_ONE);
        service.save(
                "52998224725",
                "Name Test",
                "1234567890123456",
                getDate("25/02/2019"),
                "123"
        );
    }
}
