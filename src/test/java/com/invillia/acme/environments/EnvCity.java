package com.invillia.acme.environments;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.domain.model.Uf;
import com.invillia.acme.domain.repositories.UfRepository;
import com.invillia.acme.domain.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnvCity {

    @Autowired
    private EnvUf envUf;

    @Autowired
    private UfRepository ufRepository;

    @Autowired
    private CityService service;

    public void init() throws RecordFoundException {
        envUf.init();
        Uf sp = ufRepository.findByInitial("SP");
        Uf pr = ufRepository.findByInitial("PR");
        Uf rs = ufRepository.findByInitial("RS");

        service.save("ARARAQUARA", sp);
        service.save("MARINGA", pr);
        service.save("SARANDI", pr);
        service.save("SARANDI", rs);
    }
}
