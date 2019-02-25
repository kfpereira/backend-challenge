package com.invillia.acme.domain.repositories;

import com.invillia.acme.domain.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    Address findByStreetNameAndNumberAndAddOnAndCep(String streetName, String number, String addOn, String cep);

    List<Address> findByStreetNameAndNumber(String streetName, String number);
}
