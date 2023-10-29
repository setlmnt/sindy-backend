package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.MonthlyFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            @Param("associateId") long associateId,
            @Param("paymentMonth") int paymentMonth,
            @Param("paymentYear") int paymentYear
    ); // Método para buscar a mensalidade de um associado pelo mês e ano de pagamento

    @Query(
            "SELECT m FROM MonthlyFee m " +
            "JOIN m.paymentDates d " +
            "WHERE m.associate.id = :associateId " +
            "ORDER BY d.year DESC, d.month DESC " +
            "LIMIT 1"
    ) // Query para buscar a mensalidade de um associado pelo mês de pagamento
    Optional<MonthlyFee> findMostRecentMonthlyFeeFromAssociateId(Long associateId);

    Optional<MonthlyFee> findByIdAndDeletedFalse(long id);

    /*Optional<Page<MonthlyFee>> findAllByPaymentYearAndPaymentMonth(int paymentYear, int paymentMonth, Pageable pageable); // Método para buscar todas as mensalidades pelo ano e mês de pagamento

    Optional<Page<MonthlyFee>> findAllByPaymentYear(int paymentYear, Pageable pageable); // Método para buscar todas as mensalidades pelo ano de pagamento

    Optional<Page<MonthlyFee>> findAllByPaymentMonth(int paymentMonth, Pageable pageable); // Método para buscar todas as mensalidades pelo mês de pagamento

    Optional<Page<MonthlyFee>> findAllByAssociateIdAndPaymentYear(long associateId, int paymentYear, Pageable pageable); // Método para buscar todas as mensalidades de um associado pelo ano de pagamento

    Optional<Page<MonthlyFee>> findAllByAssociateIdAndPaymentMonth(long associateId, int paymentMonth, Pageable pageable); // Método para buscar todas as mensalidades de um associado pelo mês de pagamento

    Optional<Page<MonthlyFee>> findAllByAssociateIdAndPaymentMonthAndPaymentYear(long associateId, int paymentMonth, int paymentYear, Pageable pageable); // Método para buscar todas as mensalidades de um associado pelo mês e ano de pagamento
    */
}
