package com.ifba.educampo.repository.associate;

import com.ifba.educampo.model.entity.associate.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> { // Interface de repositório para o endereço

}