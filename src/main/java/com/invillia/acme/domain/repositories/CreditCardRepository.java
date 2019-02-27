package com.invillia.acme.domain.repositories;

import com.invillia.acme.domain.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    CreditCard findByCnpjCpfAndNumberAndSecurityCode(String cnpjCpf, String number, String securityCode);

}
