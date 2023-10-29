package com.ifba.educampo.repository;

import com.ifba.educampo.model.entity.MonthlyFee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MonthlyFeeCustomRepository {
    @PersistenceContext
    private EntityManager em;

    public Page<MonthlyFee> findAllFromPaymentMonthAndYearAndDeletedFalse(
            Integer month,
            Integer year,
            Pageable pageable
    ) {
        StringBuilder query = new StringBuilder("SELECT m FROM MonthlyFee m JOIN m.paymentDates pd WHERE m.deleted = false AND pd.deleted = false");

        TypedQuery<MonthlyFee> typedQuery = filterQueryByYearAndMonth(query, year, month);

        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        List<MonthlyFee> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    public Page<MonthlyFee> findAllFromAssociateIdPaymentMonthAndYearAndDeletedFalse(
            Long associateId,
            Integer month,
            Integer year,
            Pageable pageable
    ) {
        StringBuilder query = new StringBuilder(
                "SELECT m FROM MonthlyFee m JOIN m.paymentDates pd WHERE m.deleted = false AND pd.deleted = false AND m.associate.id = :associateId");

        TypedQuery<MonthlyFee> typedQuery = filterQueryByYearAndMonth(query, year, month);

        typedQuery.setParameter("associateId", associateId);

        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());

        List<MonthlyFee> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    private TypedQuery<MonthlyFee> filterQueryByYearAndMonth(StringBuilder query, Integer year, Integer month) {
        if (month != null) {
            query.append(" AND pd.month = :month");
        }
        if (year != null) {
            query.append(" AND pd.year = :year");
        }

        TypedQuery<MonthlyFee> typedQuery = em.createQuery(query.toString(), MonthlyFee.class);

        if (month != null) {
            typedQuery.setParameter("month", month);
        }
        if (year != null) {
            typedQuery.setParameter("year", year);
        }

        return typedQuery;
    }
}
