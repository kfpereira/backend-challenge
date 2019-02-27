package com.invillia.acme.controller.makers;

import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.domain.model.OrderSale;
import com.invillia.acme.viewer.OrderOutVM;

import java.util.List;

import static com.invillia.acme.controller.makers.MakeAddress.toAddressVM;
import static com.invillia.acme.controller.makers.MakeItens.toItemsVM;

public class MakeOrderSale {

    public static OrderOutVM toOrderOutVM(OrderSale order, List<OrderItem> items) {
        OrderOutVM outVM = new OrderOutVM();
        outVM.setId(order.getId());
        outVM.setAddress(toAddressVM(order.getAddress()));
        outVM.setConfirmationDate(order.getConfirmationDate());
        outVM.setValue(order.getValue());
        outVM.setStatus(order.getStatus());
        outVM.setItems(toItemsVM(items));

        return outVM;
    }
}
