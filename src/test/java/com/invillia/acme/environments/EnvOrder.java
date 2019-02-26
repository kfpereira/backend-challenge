package com.invillia.acme.environments;

import com.invillia.acme.config.exceptions.EmptyDataException;
import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.config.exceptions.RecordNotFoundException;
import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.domain.model.Store;
import com.invillia.acme.domain.services.OrderService;
import com.invillia.acme.domain.services.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;

@Component
public class EnvOrder {

    @Autowired
    private EnvStore envStore;

    @Autowired
    private StoreService storeService;

    @Autowired
    private OrderService service;

    public void init() throws RecordFoundException, RecordNotFoundException, EmptyDataException {
        envStore.init();

        Store store = storeService.findByName("test one");
        service.save(store.getAddress(),
                Arrays.asList(getOrderItem("Item One", BigDecimal.valueOf(10), 1))
        );
    }

    public OrderItem getOrderItem(String item_one1, BigDecimal unitPrice, int quantity) {
        return OrderItem.builder()
                    .description(item_one1)
                    .unitPrice(unitPrice)
                    .quantity(quantity)
                    .build();
    }
}
