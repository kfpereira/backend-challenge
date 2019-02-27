package com.invillia.acme.controller.makers;

import com.invillia.acme.domain.model.OrderItem;
import com.invillia.acme.viewer.ItemsOutVM;
import com.invillia.acme.viewer.ItemsVM;

import java.util.List;
import java.util.stream.Collectors;

public class MakeItens {

    public static OrderItem toOrderItem(ItemsVM vm) {
        return OrderItem.builder()
                .description(vm.getDescription())
                .unitPrice(vm.getUnitPrice())
                .quantity(vm.getQuantity())
                .build();
    }

    public static List<ItemsOutVM> toItemsVM(List<OrderItem> items) {
        return items.stream().map(MakeItens::toItemVM).collect(Collectors.toList());
    }

    private static ItemsOutVM toItemVM(OrderItem item) {
        ItemsOutVM vm = new ItemsOutVM();
        vm.setId(item.getId());
        vm.setDescription(item.getDescription());
        vm.setUnitPrice(item.getUnitPrice());
        vm.setQuantity(item.getQuantity());
        return vm;
    }
}
