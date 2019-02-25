package com.invillia.acme.domain.services;

import com.invillia.acme.domain.model.Address;
import com.invillia.acme.domain.model.City;
import com.invillia.acme.domain.model.Store;
import com.invillia.acme.domain.repositories.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreService {

    private final StoreRepository repository;
    private final AddressService addressService;
    private final CityService cityService;

    @Autowired
    public StoreService(StoreRepository repository, AddressService addressService, CityService cityService) {
        this.repository = repository;
        this.addressService = addressService;
        this.cityService = cityService;
    }

    public Store save(String name, Address address) {
        Store store = Store.builder()
                .name(name)
                .address(address)
                .build();

        return repository.saveAndFlush(store);
    }

    public Store update(Store store) {
        Address storeAddress = store.getAddress();
        City addressCity = storeAddress.getCity();

        Address address = addressService.save(
                storeAddress.getStreetName(),
                storeAddress.getNumber(),
                storeAddress.getAddOn(),
                storeAddress.getNeighborhood(),
                storeAddress.getCep(),
                cityService.save(addressCity.getName(), addressCity.getUf().getInitial())
        );

        store.setAddress(address);
        return repository.saveAndFlush(store);
    }

    public Store findByName(String name) {
        return repository.findByName(name.toUpperCase());
    }

    public Store findByAddress(String street, String number, String addOn, String cep) {
        Address address = addressService.findByAddress(street, number, addOn, cep);
        return repository.findByAddress(address);
    }

    public List<Store> findAll() {
        return repository.findAll();
    }
}
