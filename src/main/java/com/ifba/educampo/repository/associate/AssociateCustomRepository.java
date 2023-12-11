package com.ifba.educampo.repository.associate;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.entity.associate.Associate;
import com.ifba.educampo.enums.PeriodEnum;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log
public class AssociateCustomRepository {

    @PersistenceContext
    private EntityManager em;

    private static void setPagination(Pageable pageable, TypedQuery<Associate> typedQuery) {
        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    }

    public Page<Associate> findAllFromNameAndCpfAndUnionCard(
            String name,
            String cpf,
            Long unionCard,
            Pageable pageable
    ) {
        StringBuilder query = new StringBuilder(
                "SELECT a FROM Associate a LEFT JOIN FETCH a.address LEFT JOIN FETCH a.affiliation " +
                        "LEFT JOIN FETCH a.dependents LEFT JOIN FETCH a.localOffice LEFT JOIN FETCH a.placeOfBirth " +
                        "LEFT JOIN FETCH a.workRecord LEFT JOIN FETCH a.profilePicture WHERE a.deleted = false "
        );

        TypedQuery<Associate> typedQuery = filterQueryByNameAndCpfAndUnionCard(query, pageable, name, cpf, unionCard);

        setPagination(pageable, typedQuery);

        List<Associate> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    public Page<Associate> findAllBirthdayAssociates(Pageable pageable, PeriodEnum period) {
        StringBuilder query = new StringBuilder(
                "SELECT a FROM Associate a LEFT JOIN FETCH a.address LEFT JOIN FETCH a.affiliation " +
                        "LEFT JOIN FETCH a.dependents LEFT JOIN FETCH a.localOffice LEFT JOIN FETCH a.placeOfBirth " +
                        "LEFT JOIN FETCH a.workRecord LEFT JOIN FETCH a.profilePicture WHERE a.deleted = false " +
                        "AND MONTH(a.birthAt) = MONTH(CURRENT_DATE) "
        );

        TypedQuery<Associate> typedQuery = filterQueryByBirthdayAssociates(query, pageable, period);

        setPagination(pageable, typedQuery);

        List<Associate> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
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
            StringBuilder query,
            Pageable pageable,
            String name,
            String cpf,
            Long unionCard
    ) {
        if (name != null) {
            query.append(" AND LOWER(a.name) LIKE LOWER(:name)");
        }

        if (cpf != null) {
            query.append(" AND LOWER(a.cpf) LIKE LOWER(:cpf)");
        }

        if (unionCard != null) {
            query.append(" AND CAST(a.unionCard AS string) LIKE :unionCard");
        }

        query.append(getOrders(pageable));

        TypedQuery<Associate> typedQuery = em.createQuery(query.toString(), Associate.class);

        if (name != null) {
            typedQuery.setParameter("name", "%" + name + "%");
        }

        if (cpf != null) {
            typedQuery.setParameter("cpf", cpf + "%");
        }

        if (unionCard != null) {
            typedQuery.setParameter("unionCard", unionCard + "%");
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
