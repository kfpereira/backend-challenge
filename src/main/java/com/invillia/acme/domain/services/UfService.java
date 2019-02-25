package com.invillia.acme.domain.services;

import com.invillia.acme.config.errors.ErrorMessages;
import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.domain.model.Uf;
import com.invillia.acme.domain.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UfService {

    private final UfRepository repository;

    @Autowired
    public UfService(UfRepository repository) {
        this.repository = repository;
    }

    public Uf save(String initial, String name) throws RecordFoundException {
        Uf uf = getUf(initial, name);
        validate(uf);
        return repository.saveAndFlush(uf);
    }

    private Uf getUf(String initial, String name) {
        return Uf.builder()
                    .initial(initial.toUpperCase())
                    .name(name.toUpperCase())
                    .build();
    }

    private void validate(Uf uf) throws RecordFoundException {
        if (repository.findByInitial(uf.getInitial()) != null)
            throw new RecordFoundException(ErrorMessages.RECORD_FOUND.getMessage());
    }
}
