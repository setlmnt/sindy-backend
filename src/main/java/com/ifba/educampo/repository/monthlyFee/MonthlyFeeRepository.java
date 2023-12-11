package com.ifba.educampo.repository.monthlyFee;

import com.ifba.educampo.entity.MonthlyFee;
import com.ifba.educampo.entity.associate.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Long> { // Interface de repositório para a mensalidade
    @Query(
            "SELECT a FROM Associate a LEFT JOIN MonthlyFee m ON a.id = m.associate.id WHERE m.id = :id AND m.deleted = false AND a.deleted = false"
    )
    Optional<Associate> findAssociateById(@Param("id") Long id);

    @Query(
            "SELECT mf FROM MonthlyFee mf where mf.associate.id = :id AND mf.deleted = false ORDER BY mf.createdAt DESC LIMIT 1"
    )
    MonthlyFee findLastByAssociateId(Long id);

    @Query(
            "SELECT mf FROM MonthlyFee mf where mf.associate.id = :id AND mf.deleted = false AND mf.initialDate >= :finalDate ORDER BY mf.createdAt ASC LIMIT 1"
    )
    MonthlyFee findNextByAssociateId(Long id, LocalDate finalDate);
}
