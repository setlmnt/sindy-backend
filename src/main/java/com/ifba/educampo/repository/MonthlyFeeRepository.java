package com.ifba.educampo.repository;

import com.ifba.educampo.domain.MonthlyFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthlyFeeRepository extends JpaRepository<MonthlyFee, Long> { // Interface de repositório para a mensalidade
    @Query("SELECT m FROM MonthlyFee m WHERE m.associate.id = ?1") // Query para buscar a mensalidade pelo associado id
    Optional<Page<MonthlyFee>> findAllByAssociateId(long associateId, Pageable pageable); // Método para buscar todas as mensalidades de um associado

    Optional<MonthlyFee> findByAssociateIdAndPaymentMonthAndPaymentYear(long associateId, int paymentMonth, int paymentYear); // Método para buscar uma mensalidade pelo id do associado e mês e ano de pagamento

    Optional<Page<MonthlyFee>> findAllByPaymentYearAndPaymentMonth(int paymentYear, int paymentMonth, Pageable pageable); // Método para buscar todas as mensalidades pelo ano e mês de pagamento

    Optional<Page<MonthlyFee>> findAllByPaymentYear(int paymentYear, Pageable pageable); // Método para buscar todas as mensalidades pelo ano de pagamento

    Optional<Page<MonthlyFee>> findAllByPaymentMonth(int paymentMonth, Pageable pageable); // Método para buscar todas as mensalidades pelo mês de pagamento

    Optional<Page<MonthlyFee>> findAllByAssociateIdAndPaymentYear(long associateId, int paymentYear, Pageable pageable); // Método para buscar todas as mensalidades de um associado pelo ano de pagamento

    Optional<Page<MonthlyFee>> findAllByAssociateIdAndPaymentMonth(long associateId, int paymentMonth, Pageable pageable); // Método para buscar todas as mensalidades de um associado pelo mês de pagamento

    Optional<Page<MonthlyFee>> findAllByAssociateIdAndPaymentMonthAndPaymentYear(long associateId, int paymentMonth, int paymentYear, Pageable pageable); // Método para buscar todas as mensalidades de um associado pelo mês e ano de pagamento
}
