package com.invillia.acme.domain.repositories;

import com.invillia.acme.domain.model.Uf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UfRepository extends JpaRepository<Uf, Long> {

    Uf findByInitial(String initial);
}
