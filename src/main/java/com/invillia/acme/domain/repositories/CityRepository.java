package com.invillia.acme.domain.repositories;

import com.invillia.acme.domain.model.City;
import com.invillia.acme.domain.model.Uf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByName(String city);

    City findByNameAndUf(String name, Uf uf);

    City findByNameAndUfInitial(String cityName, String uf);
}
