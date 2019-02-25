package com.invillia.acme.config;

import com.invillia.acme.config.inits.InitUfBrazil;
import com.invillia.acme.domain.repositories.UfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private final InitUfBrazil initUfBrazil;
    private final UfRepository ufRepository;
    private final EnvironmentReader environmentReader;

    @Autowired
    public DataLoader(InitUfBrazil initUfBrazil, UfRepository ufRepository, EnvironmentReader environmentReader) {
        this.initUfBrazil = initUfBrazil;
        this.ufRepository = ufRepository;
        this.environmentReader = environmentReader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (!environmentReader.isAmbienteDeTeste() && ufRepository.findAll().isEmpty()) initUfBrazil.init();
    }
}
