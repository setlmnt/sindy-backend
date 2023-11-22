package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.MonthlyFee;
import com.ifba.educampo.model.entity.associate.Associate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Long> { // Interface de repositório para a mensalidade
    @Query(
            "SELECT m FROM MonthlyFee m " +
                    "JOIN m.paymentDates d " +
                    "WHERE m.associate.id = :associateId " +
                    "AND d.month = :paymentMonth " +
                    "AND d.year = :paymentYear"
    )
        // Query para buscar a mensalidade de um associado pelo mês e ano de pagamento
    Optional<MonthlyFee> findByAssociateIdAndPaymentMonthAndPaymentYear(
            @Param("associateId") Long associateId,
            @Param("paymentMonth") Integer paymentMonth,
            @Param("paymentYear") Integer paymentYear
    ); // Método para buscar a mensalidade de um associado pelo mês e ano de pagamento

    Optional<MonthlyFee> findByIdAndDeletedFalse(Long id);

    @Query(
            "SELECT a FROM Associate a LEFT JOIN MonthlyFee m ON a.id = m.associate.id WHERE m.id = :id AND m.deleted = false AND a.deleted = false"
    )
    Optional<Associate> findAssociateByIdAndDeletedFalse(@Param("id") Long id);
}
