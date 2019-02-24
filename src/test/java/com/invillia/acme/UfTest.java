package com.invillia.acme;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.core.FunctionalTest;
import com.invillia.acme.domain.model.Uf;
import com.invillia.acme.domain.repositories.UfRepository;
import com.invillia.acme.domain.services.UfService;
import com.invillia.acme.environments.EnvUf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@FunctionalTest
class UfTest {

    @Autowired
    private UfRepository repository;

    @Autowired
    private UfService service;

    @Autowired
    private EnvUf envUf;

    @BeforeEach
    void setup() {
        envUf.init();
    }

    @Test
    void happyDay() {
        assertEquals("Number of States", 27, repository.findAll().size());
    }

    @Test
    void shouldNotCreateUfExisting() {
        assertThrows(RecordFoundException.class,
                () -> service.save("SP", "SÃO PAULO"),
                "This record exists in database"
        );
    }

    @Test
    void shouldFindAStateByInitial() {
        Uf sp = repository.findByInitial("SP");
        assertEquals("SP", sp.getInitial());
        assertEquals("SÃO PAULO", sp.getName());
    }
}
