package com.invillia.acme.controller.makers;

import com.invillia.acme.domain.model.Store;
import com.invillia.acme.viewer.StoreOutVM;

import static com.invillia.acme.controller.makers.MakeAddress.toAddress;
import static com.invillia.acme.controller.makers.MakeAddress.toAddressVM;

public class MakeStore {

    private MakeStore() {}

    public static StoreOutVM toStoreOutVM(Store store) {
        StoreOutVM vm = new StoreOutVM();
        vm.setId(store.getId());
        vm.setName(store.getName());
        vm.setAddress(toAddressVM(store.getAddress()));

        return vm;
    }

    public static Store toStore(StoreOutVM vm) {
        return Store.builder()
                .id(vm.getId())
                .name(vm.getName())
                .address(toAddress(vm.getAddress()))
                .build();
    }
}
