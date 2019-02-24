package com.invillia.acme.domain.services;

import com.invillia.acme.domain.model.Address;
import com.invillia.acme.domain.model.City;
import com.invillia.acme.domain.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository repository;

    @Autowired
    public AddressService(AddressRepository repository) {
        this.repository = repository;
    }

    public Address save(String streetName, String number, String addOn, String neighborhood, String cep, City city) {
        Address address = Address.builder()
                .streetName(streetName.toUpperCase())
                .number(number.toUpperCase())
                .addOn(getAddOn(addOn))
                .neighborhood(neighborhood.toUpperCase())
                .cep(cep)
                .city(city)
                .build();

        if (!exists(address))
            return repository.saveAndFlush(address);

        return null;
    }

    private boolean exists(Address address) {
        Address ad = repository.findByStreetNameAndNumberAndAddOnAndCep(
                address.getStreetName(),
                address.getNumber(),
                address.getAddOn(),
                address.getCep()
        );
        return ad != null;
    }

    private String getAddOn(String addOn) {
        return addOn == null? null : addOn.toUpperCase();
    }

}
