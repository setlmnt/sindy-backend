package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.associate.Associate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AssociateRepository extends JpaRepository<Associate, Long>, CustomAssociateRepository {
    Page<Associate> findAll(Specification<Associate> spec, Pageable pageable);

    @Query(
            "SELECT a FROM Associate a JOIN FETCH a.address LEFT JOIN FETCH a.address LEFT JOIN FETCH a.affiliation LEFT JOIN FETCH a.dependents LEFT JOIN FETCH a.localOffice LEFT JOIN FETCH a.placeOfBirth LEFT JOIN FETCH a.profilePicture LEFT JOIN FETCH a.workRecord WHERE a.id = :id"
    )
    Optional<Associate> findById(@Param("id") Long id);

    Associate findByCpf(String cpf);

    Associate findByUnionCard(Long unionCard);

    Associate findByRg(String rg);

    @Modifying
    @Query(
            "UPDATE Associate a SET a.profilePicture = (SELECT i FROM File i WHERE i.id = :imageId) WHERE a.id = :id"
    )
    void savePhoto(@Param("id") Long id, @Param("imageId") Long imageId);

    @Modifying
    @Query(
            "UPDATE Associate a SET a.localOffice = (SELECT l FROM LocalOffice l WHERE l.id = :localOfficeId) WHERE a.id = :associateId"
    )
    void addLocalOfficeToAssociates(@Param("localOfficeId") Long localOfficeId, @Param("associateId") Long associateId);

    @Modifying
    @Query(
            "UPDATE Associate a SET a.isPaid = :isPaid WHERE a.id IN :ids"
    )
    void updateAssociatesIsPaidByIds(@Param("ids") List<Long> ids, @Param("isPaid") boolean isPaid);
}
