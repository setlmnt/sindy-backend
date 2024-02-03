package com.ifba.educampo.domain.repository;

import com.ifba.educampo.domain.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
