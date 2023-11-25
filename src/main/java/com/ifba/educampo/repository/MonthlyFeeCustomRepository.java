package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.MonthlyFee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MonthlyFeeCustomRepository {
    @PersistenceContext
    private EntityManager em;

    private static void setPagination(Pageable pageable, TypedQuery<MonthlyFee> typedQuery) {
        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    }

    public Page<MonthlyFee> findAllFromInitialDateAndFinalDate(
            LocalDate initialDate,
            LocalDate finalDate,
            Pageable pageable
    ) {
        StringBuilder query = new StringBuilder(
                "SELECT mf FROM MonthlyFee mf WHERE mf.deleted = false"
        );

        TypedQuery<MonthlyFee> typedQuery = filterQueryByInitialDateAndFinalDate(query, initialDate, finalDate);

        setPagination(pageable, typedQuery);

        List<MonthlyFee> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    public Page<MonthlyFee> findAllFromAssociateIdAndInitialDateAndFinalDate(
            Long associateId,
            LocalDate initialDate,
            LocalDate finalDate,
            Pageable pageable
    ) {
        StringBuilder query = new StringBuilder(
                "SELECT mf FROM MonthlyFee mf WHERE mf.deleted = false AND mf.associate.id = :associateId");

        TypedQuery<MonthlyFee> typedQuery = filterQueryByInitialDateAndFinalDate(query, initialDate, finalDate);

        typedQuery.setParameter("associateId", associateId);

        setPagination(pageable, typedQuery);

        List<MonthlyFee> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    private TypedQuery<MonthlyFee> filterQueryByInitialDateAndFinalDate(
            StringBuilder query,
            LocalDate initialDate,
            LocalDate finalDate
    ) {
        if (initialDate != null) {
            query.append(" AND mf.initialDate = :initialDate");
        }
        if (finalDate != null) {
            query.append(" AND mf.finalDate = :finalDate");
        }

        TypedQuery<MonthlyFee> typedQuery = em.createQuery(query.toString(), MonthlyFee.class);

        if (initialDate != null) {
            typedQuery.setParameter("initialDate", initialDate);
        }
        if (finalDate != null) {
            typedQuery.setParameter("finalDate", finalDate);
        }

        return typedQuery;
    }
}
