package com.invillia.acme.domain.services;

import com.invillia.acme.domain.model.Address;
import com.invillia.acme.domain.model.City;
import com.invillia.acme.domain.repositories.AddressRepository;
import com.invillia.acme.domain.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

    private final AddressRepository repository;
    private final CityService cityService;
    private final UfRepository ufRepository;

    @Autowired
    public AddressService(AddressRepository repository, CityService cityService, UfRepository ufRepository) {
        this.repository = repository;
        this.cityService = cityService;
        this.ufRepository = ufRepository;
    }

    public Address save(String streetName, String number, String addOn, String neighborhood, String cep, String cityName, String uf) {
        City city = cityService.save(cityName, ufRepository.findByInitial(uf.toUpperCase()));
        return save(streetName, number, addOn, neighborhood, cep, city);
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

        return repository.findByStreetNameAndNumberAndAddOnAndCep(streetName.toUpperCase(),
                number.toUpperCase(),
                getAddOn(addOn),
                cep);
    }

    public Address findByAddress(String streetName, String number, String addOn, String cep) {
        return repository.findByStreetNameAndNumberAndAddOnAndCep(
                    streetName,
                    number,
                    addOn,
                    cep
            );
    }

    private boolean exists(Address address) {
        return findByAddress(address.getStreetName(), address.getNumber(), address.getAddOn(), address.getCep()) != null;
    }

    private String getAddOn(String addOn) {
        return addOn == null? null : addOn.toUpperCase();
    }

}
