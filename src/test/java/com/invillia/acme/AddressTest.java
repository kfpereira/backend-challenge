package com.invillia.acme;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.core.FunctionalTest;
import com.invillia.acme.domain.model.City;
import com.invillia.acme.domain.repositories.AddressRepository;
import com.invillia.acme.domain.services.AddressService;
import com.invillia.acme.domain.services.CityService;
import com.invillia.acme.environments.EnvAddress;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@FunctionalTest
class AddressTest {

    private static final String MARINGA = "maringa";
    private static final String AV_LONDRINA = "AV. LONDRINA";

    @Autowired
    private EnvAddress envAddress;

    @Autowired
    private AddressRepository repository;

    @Autowired
    private CityService cityService;

    @Autowired
    private AddressService service;

    @BeforeEach
    void setup() throws RecordFoundException {
        envAddress.init();
    }

    @Test
    void happyDay() {
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldForbiddenCreateExistingAddress() {
        assertEquals(2, repository.findAll().size());

        City maringaPr = cityService.find(MARINGA, "pr");
        service.save(AV_LONDRINA, "980", "SL 01", "ZONA 08", "87050-390", maringaPr);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldForbiddenCreateExistingAddressToAddOnNull() {
        assertEquals(2, repository.findAll().size());

        City maringaPr = cityService.find(MARINGA, "pr");
        service.save("AV. BRASIL", "2500", null, "CENTRO", "87500-000", maringaPr);

        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldFindAddressByStreetNameAndNumber() {
        City maringaPr = cityService.find(MARINGA, "pr");
        service.save(AV_LONDRINA, "980", "SL 02", "ZONA 08", "87050-390", maringaPr);

        assertEquals(2, repository.findByStreetNameAndNumber(AV_LONDRINA, "980").size());
    }

}
