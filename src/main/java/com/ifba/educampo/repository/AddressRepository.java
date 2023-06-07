package com.ifba.educampo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ifba.educampo.domain.Address;

public interface AddressRepository extends JpaRepository<Address, Long>{ // Interface de repositório para o endereço

}
