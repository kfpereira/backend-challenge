package com.invillia.acme.domain.repositories;

import com.invillia.acme.domain.model.Address;
import com.invillia.acme.domain.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findByName(String name);
    Store findByAddress(Address address);

}
