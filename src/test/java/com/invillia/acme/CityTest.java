package com.invillia.acme;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.core.FunctionalTest;
import com.invillia.acme.domain.model.City;
import com.invillia.acme.domain.model.Uf;
import com.invillia.acme.domain.repositories.CityRepository;
import com.invillia.acme.domain.repositories.UfRepository;
import com.invillia.acme.domain.services.CityService;
import com.invillia.acme.environments.EnvCity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@FunctionalTest
class CityTest {

    private static final String MARINGA = "MARINGA";

    @Autowired
    private CityRepository repository;

    @Autowired
    private UfRepository ufRepository;

    @Autowired
    private CityService service;

    @Autowired
    private EnvCity envCity;

    @BeforeEach
    void setup() throws RecordFoundException {
        envCity.init();
    }

    @Test
    void happyDay() {
        assertEquals("Number of cities", 4, repository.findAll().size());
    }

    @Test
    void shouldNotCreateExistingCity() {
        Uf pr = ufRepository.findByInitial("PR");

        assertNotNull(repository.findByNameAndUf(MARINGA, pr));
        assertEquals(1, repository.findByName(MARINGA).size());

        service.save(MARINGA, pr);
        assertNotNull(repository.findByNameAndUf(MARINGA, pr));
        assertEquals(1, repository.findByName(MARINGA).size());
    }

    @Test
    void shouldFindByName() {
        List<City> cities = repository.findByName("ARARAQUARA");
        assertEquals(1, cities.size());

        cities = repository.findByName("SARANDI");
        assertEquals(2, cities.size());
    }

    @Test
    void shouldNotSaveCityWithoutUf() {
        assertThrows(ConstraintViolationException.class,
                () -> service.save("PARANAVAI", (Uf) null),
                "UF can't be null"
        );
    }

    @Test
    void shouldNotSaveCityWithInvalidUf() {
        assertThrows(ConstraintViolationException.class,
                () -> service.save("PARANAVAI", ufRepository.findByInitial("AA")),
                "UF can't be null"
        );
    }

}
