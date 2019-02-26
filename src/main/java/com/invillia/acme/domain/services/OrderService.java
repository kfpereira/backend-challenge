package com.invillia.acme.domain.services;

import com.invillia.acme.config.errors.ErrorMessages;
import com.invillia.acme.config.exceptions.EmptyDataException;
import com.invillia.acme.config.exceptions.RecordNotFoundException;
import com.invillia.acme.domain.model.Address;
import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.repositories.OrderRepository;
import com.invillia.acme.domain.types.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository repository;

    @Autowired
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public OrderSale save(Address address, List<OrderItem> itens) throws RecordNotFoundException, EmptyDataException {
        validate(address, itens);

        OrderSale build = OrderSale.builder()
                .confirmationDate(new Date())
                .address(address)
                .status(OrderStatus.OPENED)
                .itens(itens)
                .build();

        return repository.saveAndFlush(build);
    }

    private void validate(Address address, List<OrderItem> itens) throws RecordNotFoundException, EmptyDataException {
        if (address == null)
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND.toString());

        if (itens == null || itens.isEmpty())
            throw new EmptyDataException(ErrorMessages.EMPTY_DATA.toString());
    }

}
