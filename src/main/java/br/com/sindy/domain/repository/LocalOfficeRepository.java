package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.LocalOffice;
import br.com.sindy.domain.entity.associate.Associate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LocalOfficeRepository extends JpaRepository<LocalOffice, Long> {
    @Query(
            "SELECT a FROM Associate a WHERE a.localOffice.id = ?1"
    )
    Optional<Page<Associate>> listAllAssociates(long localOfficeId, Pageable pageable);

    @Query(
            "SELECT lo FROM LocalOffice lo WHERE lo.deleted = false"
    )
    Page<LocalOffice> findAllAndDeletedFalse(Pageable pageable);
}
