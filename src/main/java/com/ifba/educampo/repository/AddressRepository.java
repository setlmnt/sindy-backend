package com.ifba.educampo.repository;

import com.ifba.educampo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> { // Interface de repositório para o endereço

}
