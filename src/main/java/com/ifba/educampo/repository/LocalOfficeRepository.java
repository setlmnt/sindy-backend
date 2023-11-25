package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.LocalOffice;
import com.ifba.educampo.model.entity.associate.Associate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocalOfficeRepository extends JpaRepository<LocalOffice, Long> { // Interface de repositório para o endereço
    @Query(
            "SELECT a FROM Associate a WHERE a.localOffice.id = ?1"
    )
        // Query para buscar o Associado pela delegacia (escritório local) a qual ele pertence
    Optional<Page<Associate>> listAllAssociates(long localOfficeId, Pageable pageable);

    @Query(
            "SELECT lo FROM LocalOffice lo WHERE lo.deleted = false"
    )
// Query para buscar a delegacia (escritório local) não esteja deletado
    Page<LocalOffice> findAllAndDeletedFalse(Pageable pageable);
}
