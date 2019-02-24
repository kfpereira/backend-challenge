package com.invillia.acme.domain.services;

import com.invillia.acme.domain.model.City;
import com.invillia.acme.domain.model.Uf;
import com.invillia.acme.domain.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {

    private final CityRepository repository;

    @Autowired
    public CityService(CityRepository repository) {
        this.repository = repository;
    }

    public City save(String name, Uf uf) {
        City city = getCity(name, uf);
        if (!exists(city))
            return repository.saveAndFlush(city);

        return repository.findByNameAndUf(name.toUpperCase(), uf);
    }

    private City getCity(String name, Uf uf) {
        return City.builder()
                    .name(name.toUpperCase())
                    .uf(uf)
                    .build();
    }

    private boolean exists(City city) {
        return repository.findByName(city.getName())
                .stream()
                .anyMatch(currentCity -> city.getUf().equals(currentCity.getUf()));
    }

    public City find(String cityName, String uf) {
        return repository.findByNameAndUfInitial(cityName.toUpperCase(), uf.toUpperCase());
    }
}
