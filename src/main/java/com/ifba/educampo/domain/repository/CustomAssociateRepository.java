package com.ifba.educampo.domain.repository;

import com.ifba.educampo.domain.entity.associate.Associate;
import com.ifba.educampo.domain.enums.PeriodEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAssociateRepository {
    Page<Associate> findAllFromNameAndCpfAndUnionCard(
            String name,
            String cpf,
            Long unionCard,
            Pageable pageable
    );

    Page<Associate> findAllBirthdayAssociates(Pageable pageable, PeriodEnum period);
}
