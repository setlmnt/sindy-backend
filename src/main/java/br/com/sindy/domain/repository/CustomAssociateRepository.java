package br.com.sindy.domain.repository;

import br.com.sindy.domain.entity.associate.Associate;
import br.com.sindy.domain.enums.PeriodEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAssociateRepository {
    Page<Associate> findAllFromNameAndCpfAndUnionCard(String query, Pageable pageable);

    Page<Associate> findAllBirthdayAssociates(Pageable pageable, PeriodEnum period);
}
