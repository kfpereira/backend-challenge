package com.invillia.acme.controller.makers;

import com.invillia.acme.domain.model.Address;
import com.invillia.acme.viewer.AddressVM;

import static com.invillia.acme.controller.makers.MakeCity.toCity;

class MakeAddress {

    private MakeAddress() {}

    static AddressVM toAddressVM(Address address) {
        AddressVM vm = new AddressVM();
        vm.setCep(address.getCep());
        vm.setStreetName(address.getStreetName());
        vm.setNumberHome(address.getNumber());
        vm.setAddressAddOn(address.getAddOn());
        vm.setNeighborhood(address.getNeighborhood());
        vm.setCity(MakeCity.toCityVM(address.getCity()));

        return vm;
    }

    static Address toAddress(AddressVM vm) {
        return Address.builder()
                .cep(vm.getCep())
                .streetName(vm.getStreetName())
                .number(vm.getNumberHome())
                .addOn(vm.getAddressAddOn())
                .neighborhood(vm.getNeighborhood())
                .city(toCity(vm.getCity()))
                .build();
    }
}
