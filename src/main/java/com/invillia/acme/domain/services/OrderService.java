package com.invillia.acme.domain.services;

import com.invillia.acme.config.errors.ErrorMessages;
import com.invillia.acme.config.exceptions.EmptyDataException;
import com.invillia.acme.config.exceptions.RecordNotFoundException;
import com.invillia.acme.domain.model.Address;
import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.observer.Observer;
import com.invillia.acme.domain.repositories.OrderSaleRepository;
import com.invillia.acme.domain.types.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static com.invillia.acme.domain.utils.DateUtils.toDate;


@Service
public class OrderService implements Observer {

    private final OrderSaleRepository repository;
    private final OrderItemService itemService;
    private final Clock clock;

    @Autowired
    public OrderService(OrderSaleRepository repository, OrderItemService itemService, Clock clock) {
        this.repository = repository;
        this.itemService = itemService;
        this.clock = clock;
    }

    @Transactional
    public OrderSale save(Address address, List<OrderItem> itens) throws RecordNotFoundException, EmptyDataException {
        validate(address, itens);

        OrderSale build = OrderSale.builder()
                .confirmationDate(toDate(LocalDate.now(clock)))
                .address(address)
                .status(Status.OPENED)
                .build();

        OrderSale orderSale = repository.saveAndFlush(build);
        itemService.save(orderSale, itens);
        return orderSale;
    }

    private void validate(Address address, List<OrderItem> itens) throws RecordNotFoundException, EmptyDataException {
        if (address == null)
            throw new RecordNotFoundException(ErrorMessages.RECORD_NOT_FOUND.toString());

        if (itens == null || itens.isEmpty())
            throw new EmptyDataException(ErrorMessages.EMPTY_DATA.toString());
    }

    public void update(OrderSale order, Status status) {
        order.setStatus(status);
        repository.save(order);
    }

    @Override
    public void update(PaymentService subject, OrderSale order) {
        update(order, Status.CONCLUDED);
    }

    public List<OrderItem> getItens(OrderSale orderSale) {
        return itemService.find(orderSale);
    }

    public List<OrderSale> findBetweenConfirmationDate(Date initial, Date end) {
        return repository.findByConfirmationDateBetween(initial, end);
    }

    public List<OrderSale> find(Status status) {
        return repository.findByStatus(status);
    }

}
