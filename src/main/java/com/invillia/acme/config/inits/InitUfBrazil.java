package com.invillia.acme.config.inits;

import com.invillia.acme.config.exceptions.RecordFoundException;
import com.invillia.acme.domain.services.UfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitUfBrazil {

    private final UfService service;

    @Autowired
    public InitUfBrazil(UfService service) {
        this.service = service;
    }

    public void init() throws RecordFoundException {
        service.save("AC", "ACRE");
        service.save("AL", "ALAGOAS");
        service.save("AP", "AMAPÁ");
        service.save("AM", "AMAZONAS");
        service.save("BA", "BAHIA");
        service.save("CE", "CEARÁ");
        service.save("DF", "DISTRITO FEDERAL");
        service.save("ES", "ESPÍRITO SANTO");
        service.save("GO", "GOIÁS");
        service.save("MA", "MARANHÃO");
        service.save("MT", "MATO GROSSO");
        service.save("MS", "MATO GROSSO DO SUL");
        service.save("MG", "MINAS GERAIS");
        service.save("PA", "PARÁ");
        service.save("PB", "PARAÍBA");
        service.save("PR", "PARANÁ");
        service.save("PE", "PERNAMBUCO");
        service.save("PI", "PIAUÍ");
        service.save("RJ", "RIO DE JANEIRO");
        service.save("RN", "RIO GRANDE DO NORTE");
        service.save("RS", "RIO GRANDE DO SUL");
        service.save("RO", "RONDÔNIA");
        service.save("RR", "RORAIMA");
        service.save("SC", "SANTA CATARINA");
        service.save("SP", "SÃO PAULO");
        service.save("SE", "SERGIPE");
        service.save("TO", "TOCANTINS");
    }
}
