package com.invillia.acme;

import com.invillia.acme.config.exceptions.EmptyDataException;
import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.config.exceptions.RecordNotFoundException;
import com.invillia.acme.core.FunctionalTest;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.model.Store;
import com.invillia.acme.domain.repositories.OrderRepository;
import com.invillia.acme.domain.services.OrderService;
import com.invillia.acme.domain.services.StoreService;
import com.invillia.acme.environments.EnvOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@FunctionalTest
class OrderSaleTest {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private OrderService service;

    @Autowired
    private StoreService storeService;

    @Autowired
    private EnvOrder envOrder;

    @BeforeEach
    void setup() throws RecordFoundException, RecordNotFoundException, EmptyDataException {
        envOrder.init();
    }

    @Test
    void happyDay() throws RecordFoundException {
        List<OrderSale> orderSaleList = repository.findAll();
        assertEquals("Orders quantity", 1, orderSaleList.size());
        assertEquals("Itens Order quantity", 1, orderSaleList.get(0).getItens().size());
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

}
