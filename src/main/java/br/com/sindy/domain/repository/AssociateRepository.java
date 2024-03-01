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

public interface AssociateRepository extends JpaRepository<Associate, Long>, CustomAssociateRepository {
    Page<Associate> findAll(Specification<Associate> spec, Pageable pageable);

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
    void updateAssociatesIsPaidByIds(List<Long> ids, boolean isPaid);
}
