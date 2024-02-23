package br.com.sindy.domain.repository.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.entity.associate.Associate;
import br.com.sindy.domain.enums.PeriodEnum;
import br.com.sindy.domain.repository.CustomAssociateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log
public class CustomAssociateRepositoryImpl implements CustomAssociateRepository {
    private static final String SELECT_ALL_ASSOCIATES = "SELECT a FROM Associate a LEFT JOIN FETCH a.address LEFT JOIN FETCH a.affiliation " +
            "LEFT JOIN FETCH a.dependents LEFT JOIN FETCH a.localOffice LEFT JOIN FETCH a.placeOfBirth " +
            "LEFT JOIN FETCH a.workRecord LEFT JOIN FETCH a.profilePicture WHERE a.deleted = false ";
    private static final String SELECT_ASSOCIATES_BIRTHDAY = "SELECT a FROM Associate a LEFT JOIN FETCH a.address LEFT JOIN FETCH a.affiliation " +
            "LEFT JOIN FETCH a.dependents LEFT JOIN FETCH a.localOffice LEFT JOIN FETCH a.placeOfBirth " +
            "LEFT JOIN FETCH a.workRecord LEFT JOIN FETCH a.profilePicture WHERE a.deleted = false " +
            "AND MONTH(a.birthAt) = MONTH(CURRENT_DATE) ";

    @PersistenceContext
    private EntityManager em;

    private void setPagination(Pageable pageable, TypedQuery<?> typedQuery) {
        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    }

    public Page<Associate> findAllFromNameAndCpfAndUnionCard(String query, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder(SELECT_ALL_ASSOCIATES);

        TypedQuery<Associate> typedQuery = filterQueryByNameAndCpfAndUnionCard(queryBuilder, pageable, query);
        int totalElements = typedQuery.getResultList().size();

        setPagination(pageable, typedQuery);

        List<Associate> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalElements);
    }

    public Page<Associate> findAllBirthdayAssociates(Pageable pageable, PeriodEnum period) {
        StringBuilder query = new StringBuilder(SELECT_ASSOCIATES_BIRTHDAY);

        TypedQuery<Associate> typedQuery = filterQueryByBirthdayAssociates(query, pageable, period);
        int totalElements = typedQuery.getResultList().size();

        setPagination(pageable, typedQuery);

        List<Associate> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, totalElements);
    }

    private TypedQuery<Associate> filterQueryByBirthdayAssociates(StringBuilder query, Pageable pageable, PeriodEnum period) {
        if (period == PeriodEnum.DAY) {
            query.append(" AND DAY(a.birthAt) = DAY(CURRENT_DATE)");
        } else if (period == PeriodEnum.WEEK) {
            query.append(" AND WEEK(a.birthAt) = WEEK(CURRENT_DATE)");
        }

        query.append(getOrders(pageable));
        query.append(", a.birthAt ASC");

        return em.createQuery(query.toString(), Associate.class);
    }

    private TypedQuery<Associate> filterQueryByNameAndCpfAndUnionCard(
            StringBuilder queryBuilder,
            Pageable pageable,
            String query
    ) {
        if (!StringUtils.isEmpty(query)) {
            queryBuilder.append(" AND (LOWER(a.name) LIKE LOWER(:name) OR LOWER(a.cpf) LIKE LOWER(:cpf) OR CAST(a.unionCard AS string) LIKE :unionCard)");
        }

        queryBuilder.append(getOrders(pageable));

        TypedQuery<Associate> typedQuery = em.createQuery(queryBuilder.toString(), Associate.class);

        if (!StringUtils.isEmpty(query)) {
            typedQuery.setParameter("name", "%" + query.toLowerCase() + "%");
            typedQuery.setParameter("cpf", query.toLowerCase() + "%");
            typedQuery.setParameter("unionCard", query.toLowerCase() + "%");
        }

        return typedQuery;
    }

    private String getOrders(Pageable pageable) {
        StringBuilder orders = new StringBuilder(" ORDER BY ");

        List<String> orderList = new ArrayList<>();
        pageable.getSort().forEach(order -> {
            String property = order.getProperty();
            String direction = order.getDirection().name();
            orderList.add(property + " " + direction);
        });

        if (orderList.isEmpty()) {
            orders.append("a.id ASC");
        } else {
            orders.append(String.join(", ", orderList));
        }

        return orders.toString();
    }
}
