package com.invillia.acme.controller.makers;

import com.invillia.acme.domain.model.City;
import com.invillia.acme.domain.model.Uf;
import com.invillia.acme.viewer.CityVM;

class MakeCity {

    private MakeCity() {}

    static CityVM toCityVM(City city) {
        CityVM vm = new CityVM();
        vm.setNameCity(city.getName());
        vm.setUf(city.getUf().getInitial());

        return vm;
    }

    static City toCity(CityVM vm) {
        return City.builder()
                .name(vm.getNameCity())
                .uf(Uf.builder().initial(vm.getUf()).build())
                .build();
    }
}
