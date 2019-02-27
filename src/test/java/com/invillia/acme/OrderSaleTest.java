package com.invillia.acme;

import com.invillia.acme.config.exceptions.*;
import com.invillia.acme.core.FunctionalTest;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.model.Payment;
import com.invillia.acme.domain.model.Store;
import com.invillia.acme.domain.repositories.OrderSaleRepository;
import com.invillia.acme.domain.services.CreditCardService;
import com.invillia.acme.domain.services.OrderService;
import com.invillia.acme.domain.services.PaymentService;
import com.invillia.acme.domain.services.StoreService;
import com.invillia.acme.domain.types.Status;
import com.invillia.acme.environments.AbstractIntegrationTest;
import com.invillia.acme.environments.EnvCreditCard;
import com.invillia.acme.environments.EnvOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static com.invillia.acme.domain.utils.DateUtils.getDate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@FunctionalTest
class OrderSaleTest extends AbstractIntegrationTest {

    @Autowired
    private OrderSaleRepository repository;

    @Autowired
    private OrderService service;

    @Autowired
    private StoreService storeService;

    @Autowired
    private EnvOrder envOrder;

    @Autowired
    private EnvCreditCard envCreditCard;

    @Autowired
    private CreditCardService creditCardService;

    @Autowired
    private PaymentService paymentService;

    @BeforeEach
    void setup() throws RecordFoundException, RecordNotFoundException, EmptyDataException {
        setDate(2019, Month.FEBRUARY, 21);
        envOrder.init();
    }

    @Test
    void happyDay() throws RecordFoundException {
        List<OrderSale> orderSaleList = repository.findAll();
        assertEquals("Orders quantity", 1, orderSaleList.size());
        assertFebruary21(orderSaleList);
    }

    @Test
    void shouldNotSaveOrderWithoutAddress() {
        assertThrows(RecordNotFoundException.class,
                () -> service.save(
                        null,
                        Arrays.asList(envOrder.getOrderItem("Item One", BigDecimal.valueOf(10), 1))
                ),
                "This record doesn't exist in database"
        );
    }

    @Test
    void shouldNotSaveOrderWithoutItens() {
        Store store = storeService.findByName("teste two");
        assertThrows(EmptyDataException.class,
                () -> service.save(store.getAddress(), null),
                "This data can't be null or empty"
        );
    }

    @Test
    void shouldFindTwoOrders() throws RecordNotFoundException, EmptyDataException {
        initFebruary21();
        initFebruary25();

        List<OrderSale> orderSaleList = repository.findAll();
        assertEquals("Orders quantity", 3, orderSaleList.size());
        orderSaleList.sort(Comparator.comparing(OrderSale::getConfirmationDate));
        assertFebruary21(orderSaleList);
        assertFebruary22(orderSaleList);
        assertFebruary25(orderSaleList);

        List<OrderSale> list = service.findBetweenConfirmationDate(getDate("21/02/2019"), getDate("22/02/2019"));
        assertEquals(2, list.size());

        list = service.findBetweenConfirmationDate(getDate("23/02/2019"), getDate("24/02/2019"));
        assertTrue(list.isEmpty());

        list = service.findBetweenConfirmationDate(getDate("25/02/2019"), getDate("25/02/2019"));
        assertEquals(1, list.size());
    }

    @Test
    void shouldFindOneOrderConcluded() throws RecordNotFoundException, EmptyDataException, CnpjCpfInvalidException, CreditCardInvalidException, PaymentInvalidException {
        envCreditCard.init();

        initFebruary21();
        initFebruary25();

        List<OrderSale> orderSaleList = repository.findAll();
        orderSaleList.sort(Comparator.comparing(OrderSale::getConfirmationDate));

        List<OrderSale> list = service.findBetweenConfirmationDate(getDate("21/02/2019"), getDate("21/02/2019"));
        assertEquals(1, list.size());
        OrderSale orderSale = list.get(0);

        setDate(2019, Month.FEBRUARY, 25);
        Payment payment = paymentService.save(orderSale,
                creditCardService.find("52998224725", "1234567890123456", "123"));

        assertEquals(Status.CONCLUDED, orderSale.getStatus());
        assertEquals(Status.CONCLUDED, payment.getStatus());

        List<OrderSale> saleList = service.find(Status.CONCLUDED);
        assertEquals(1, saleList.size());
    }

    private void initFebruary25() throws RecordNotFoundException, EmptyDataException {
        setDate(2019, Month.FEBRUARY, 25);
        service.save(storeService.findByName("teste two").getAddress(),
                Arrays.asList(
                        envOrder.getOrderItem("Item 01", BigDecimal.valueOf(22), 4),
                        envOrder.getOrderItem("Item 02", BigDecimal.valueOf(18), 4),
                        envOrder.getOrderItem("Item 03", BigDecimal.valueOf(13.99), 2),
                        envOrder.getOrderItem("Item 04", BigDecimal.valueOf(17.80), 6)
                )
        );
    }

    private void initFebruary21() throws RecordNotFoundException, EmptyDataException {
        setDate(2019, Month.FEBRUARY, 22);
        Store store = storeService.findByName("test one");
        service.save(store.getAddress(),
                Arrays.asList(
                        envOrder.getOrderItem("Item One", BigDecimal.valueOf(10), 1),
                        envOrder.getOrderItem("Item Two", BigDecimal.valueOf(13), 4),
                        envOrder.getOrderItem("Item Three", BigDecimal.valueOf(15), 2)
                )
        );
    }

    private void assertFebruary25(List<OrderSale> orderSaleList) {
        OrderSale orderSaleThree = orderSaleList.get(2);
        assertEquals("Itens Order quantity", 4, service.getItens(orderSaleThree).size());
        assertEquals(getDate("25/02/2019"), orderSaleThree.getConfirmationDate());
    }

    private void assertFebruary22(List<OrderSale> orderSaleList) {
        OrderSale orderSaleTwo = orderSaleList.get(1);
        assertEquals("Itens Order quantity", 3, service.getItens(orderSaleTwo).size());
        assertEquals(getDate("22/02/2019"), orderSaleTwo.getConfirmationDate());
    }

    private void assertFebruary21(List<OrderSale> orderSaleList) {
        OrderSale orderSaleOne = orderSaleList.get(0);
        assertEquals("Itens Order quantity", 1, service.getItens(orderSaleOne).size());
        assertEquals(getDate("21/02/2019"), orderSaleOne.getConfirmationDate());
    }
}
