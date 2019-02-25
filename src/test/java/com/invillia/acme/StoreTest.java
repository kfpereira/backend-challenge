package com.invillia.acme;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.core.FunctionalTest;
import com.invillia.acme.domain.model.Store;
import com.invillia.acme.domain.repositories.StoreRepository;
import com.invillia.acme.domain.services.StoreService;
import com.invillia.acme.environments.EnvStore;
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
class StoreTest {

    private static final String AV_LONDRINA = "AV. LONDRINA";
    private static final String SALA_02 = "SL 02";

    @Autowired
    private EnvStore envStore;

    @Autowired
    private StoreRepository repository;

    @Autowired
    private StoreService service;

    @BeforeEach
    void setup() throws RecordFoundException {
        envStore.init();
    }

    @Test
    void happyDay() {
        assertEquals(2, repository.findAll().size());
    }

    @Test
    void shouldFindByName() {
        Store store = service.findByName("TESTE TWO");
        assertEquals(AV_LONDRINA, store.getAddress().getStreetName());
        assertEquals("980", store.getAddress().getNumber());
        assertEquals(SALA_02, store.getAddress().getAddOn());
    }

    @Test
    void shouldFindByNameLowerCase() {
        Store store = service.findByName("teste two");
        assertEquals(AV_LONDRINA, store.getAddress().getStreetName());
        assertEquals("980", store.getAddress().getNumber());
        assertEquals(SALA_02, store.getAddress().getAddOn());
    }

    @Test
    void shouldFindByAddress() {
        Store store = service.findByAddress(AV_LONDRINA, "980", SALA_02, "87050-390");
        assertEquals(AV_LONDRINA, store.getAddress().getStreetName());
        assertEquals("980", store.getAddress().getNumber());
        assertEquals(SALA_02, store.getAddress().getAddOn());
    }

}
