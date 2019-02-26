package com.invillia.acme;

import com.invillia.acme.config.exceptions.CnpjCpfInvalidException;
import com.invillia.acme.config.exceptions.CreditCardInvalidException;
import com.invillia.acme.core.FunctionalTest;
import com.invillia.acme.domain.model.CreditCard;
import com.invillia.acme.domain.repositories.CreditCardRepository;
import com.invillia.acme.domain.services.CreditCardService;
import com.invillia.acme.environments.AbstractIntegrationTest;
import com.invillia.acme.environments.EnvCreditCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.invillia.acme.domain.utils.DateUtils.getDate;
import static java.time.Month.FEBRUARY;
import static java.time.Month.JULY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@FunctionalTest
class CreditCardTest extends AbstractIntegrationTest {

    private static final int DAY_ONE = 01;

    @Autowired
    private CreditCardService service;

    @Autowired
    private CreditCardRepository repository;

    @Autowired
    private EnvCreditCard envCreditCard;

    @Test
    void happyDay() throws CnpjCpfInvalidException, CreditCardInvalidException {
        envCreditCard.init();
        assertEquals(1, repository.findAll().size());
    }

    @Test
    void shouldBeInvalidDate() {
        setDate(2021, JULY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "Name Test",
                        "1234567890123456",
                        getDate("30/06/2021"),
                        "123"
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidEmptyName() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "",
                        "1234567890123456",
                        getDate("30/06/2021"),
                        "123"
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidNullName() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        null,
                        "1234567890123456",
                        getDate("30/06/2021"),
                        "123"
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidCpf() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CnpjCpfInvalidException.class,
                () -> service.save(
                        "52998224721",
                        "Test",
                        "1234567890123456",
                        getDate("30/06/2021"),
                        "123"
                ),
                "CNPJ/CPF invalid"
        );
    }

    @Test
    void shouldBeInvalidLessNumber() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "Test",
                        "123456789012345",
                        getDate("30/06/2021"),
                        "123"
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidMoreNumber() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "Test",
                        "12345678901234567",
                        getDate("30/06/2021"),
                        "123"
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidEmptyNumber() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "Test",
                        "",
                        getDate("30/06/2021"),
                        "123"
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidNullNumber() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "Test",
                        null,
                        getDate("30/06/2021"),
                        "123"
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidLessSecurityCode() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "Test",
                        "1234567890123456",
                        getDate("30/06/2021"),
                        "12"
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidMoreSecurityCode() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "Test",
                        "1234567890123456",
                        getDate("30/06/2021"),
                        "1234"
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidEmptySecurityCode() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "Test",
                        "1234567890123456",
                        getDate("30/06/2021"),
                        ""
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void shouldBeInvalidNullSecurityCode() {
        setDate(2019, FEBRUARY, DAY_ONE);
        assertThrows(CreditCardInvalidException.class,
                () -> service.save(
                        "52998224725",
                        "Test",
                        "1234567890123456",
                        getDate("30/06/2021"),
                        null
                ),
                "Invalid Credit Card"
        );
    }

    @Test
    void findCreditCard() throws CnpjCpfInvalidException, CreditCardInvalidException {
        setDate(2019, FEBRUARY, DAY_ONE);
        service.save(
                "52998224725",
                "Name Test",
                "1234567890123456",
                getDate("01/07/2021"),
                "123"
        );

        CreditCard creditCard = service.find("52998224725", "1234567890123456", "123");
        assertNotNull(creditCard);
        assertEquals("NAME TEST", creditCard.getName());
        assertEquals(getDate("01/07/2021"), creditCard.getValidateDate());
    }
}
