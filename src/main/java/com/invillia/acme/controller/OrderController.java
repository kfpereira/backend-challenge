package com.invillia.acme.controller;

import com.invillia.acme.config.exceptions.*;
import com.invillia.acme.controller.makers.MakeItens;
import com.invillia.acme.domain.model.Address;
import com.invillia.acme.domain.model.CreditCard;
import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.domain.services.*;
import com.invillia.acme.domain.types.Status;
import com.invillia.acme.viewer.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.invillia.acme.controller.makers.MakeOrderSale.toOrderOutVM;

@Api(tags = { "orderController" })
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;
    private final OrderItemService itemService;
    private final AddressService addressService;
    private final CityService cityService;
    private final CreditCardService creditCardService;
    private final PaymentService paymentService;
    private final OrderRefundService orderRefundService;

    @Autowired
    public OrderController(OrderService service, OrderItemService itemService, AddressService addressService,
                           CityService cityService, CreditCardService creditCardService, PaymentService paymentService,
                           OrderRefundService orderRefundService) {
        this.service = service;
        this.itemService = itemService;
        this.addressService = addressService;
        this.cityService = cityService;
        this.creditCardService = creditCardService;
        this.paymentService = paymentService;
        this.orderRefundService = orderRefundService;
    }

    @ApiOperation(value = "Create a new Order.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderOutVM createOrder(HttpServletRequest request, @Valid @RequestBody OrderVM order) throws RecordNotFoundException, EmptyDataException {
        AddressVM addressVM = order.getAddress();
        Address address = getAddress(addressVM);
        OrderSale orderSale = service.save(address, getOrderItems(order));
        return toOrderOutVM(orderSale, itemService.find(orderSale));
    }

    private List<OrderItem> getOrderItems(@Valid @RequestBody OrderVM order) {
        return order.getItems().stream().map(MakeItens::toOrderItem).collect(Collectors.toList());
    }

    @ApiOperation(value = "Retrieve a Order By ID.")
    @GetMapping("/id")
    @ResponseStatus(HttpStatus.OK)
    public OrderOutVM retrieveOrderById(HttpServletRequest request, @RequestParam Long id) {
        OrderSale order = service.find(id);
        return toOrderOutVM(order, itemService.find(order));
    }

    @ApiOperation(value = "Retrieve a Order By Status.")
    @GetMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderOutVM> retrieveOrderByStatus(HttpServletRequest request, @RequestParam Status status) {
        return service.find(status)
                .stream()
                .map(orderSale -> toOrderOutVM(orderSale, itemService.find(orderSale)))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Retrieve a Order between Confirmation Date.")
    @GetMapping("/date")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderOutVM> retrieveOrderBetweenDate(HttpServletRequest request, @RequestParam Date initialDate, Date endDate) {
        return service.findBetweenConfirmationDate(initialDate, endDate)
                .stream()
                .map(orderSale -> toOrderOutVM(orderSale, itemService.find(orderSale)))
                .collect(Collectors.toList());
    }

    @ApiOperation(value = "Create a Payment for an Order.")
    @PostMapping("/payment")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderOutVM createPayment(HttpServletRequest request, @Valid @RequestBody PaymentVM paymentVM) throws CnpjCpfInvalidException, CreditCardInvalidException, PaymentInvalidException {
        OrderSale orderSale = service.find(paymentVM.getIdOrder());

        CreditCardVM vm = paymentVM.getCreditCard();
        CreditCard creditCard = creditCardService.save(vm.getCnpjCpf(), vm.getName(), vm.getNumber(), vm.getValidateDate(), vm.getSecurityCode());
        paymentService.save(orderSale, creditCard);
        return toOrderOutVM(orderSale, itemService.find(orderSale));
    }

    @ApiOperation(value = "Refund Order.")
    @PostMapping("/refund/order")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderOutVM refundOrder(HttpServletRequest request, @Valid @RequestBody SimpleOrderVM order) {
        OrderSale orderSale = service.find(order.getIdOrder());
        orderRefundService.refund(orderSale);
        return toOrderOutVM(orderSale, itemService.find(orderSale));
    }

    @ApiOperation(value = "Refund Item of Order.")
    @PostMapping("/refund/order/item")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderOutVM refundItemFromOrder(HttpServletRequest request, @Valid @RequestBody SimpleOrderItemVM order) {
        OrderSale orderSale = service.find(order.getIdOrder());
        List<OrderItem> itens = service.getItens(orderSale);
        validate(itens, order);

        orderRefundService.refund(getItem(itens, order.getIdItem()));
        return toOrderOutVM(orderSale, itemService.find(orderSale));
    }

    private OrderItem getItem(List<OrderItem> items, Long idItem) {
        return items.stream().filter(item -> item.getId().equals(idItem)).findFirst().orElse(null);
    }

    private void validate(List<OrderItem> itens, SimpleOrderItemVM order) {
        if (!isFound(itens, order))
            throw new IllegalArgumentException();
    }

    private boolean isFound(List<OrderItem> itens, SimpleOrderItemVM order) {
        return itens.stream().anyMatch(item -> item.getId().equals(order.getIdItem()));
    }

    private Address getAddress(AddressVM addressVM) {
        return addressService.save(addressVM.getStreetName(),
                    addressVM.getNumberHome(),
                    addressVM.getAddressAddOn(),
                    addressVM.getNeighborhood(),
                    addressVM.getCep(),
                    cityService.save(addressVM.getCity().getNameCity(), addressVM.getCity().getUf()));
    }
}
