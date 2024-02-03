package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.MonthlyFee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface CustomMonthlyFeeRepository {
    Page<MonthlyFee> findAllFromInitialDateAndFinalDate(
            LocalDate initialDate,
            LocalDate finalDate,
            Pageable pageable
    );

    Page<MonthlyFee> findAllFromAssociateIdAndInitialDateAndFinalDate(
            Long associateId,
            LocalDate initialDate,
            LocalDate finalDate,
            Pageable pageable
    );
}
