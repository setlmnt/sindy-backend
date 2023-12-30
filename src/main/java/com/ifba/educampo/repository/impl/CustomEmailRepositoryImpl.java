package com.ifba.educampo.repository.impl;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.entity.Email;
import com.ifba.educampo.enums.EmailStatusEnum;
import com.ifba.educampo.repository.CustomEmailRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log
public class CustomEmailRepositoryImpl implements CustomEmailRepository {
    private static final String SELECT_ALL_EMAILS = "SELECT e FROM Email e WHERE e.deleted = false";
    @PersistenceContext
    private EntityManager em;

    public Page<Email> findAllWithFilterAndDeletedFalse(
            String owner,
            String emailTo,
            String emailFrom,
            EmailStatusEnum status,
            Pageable pageable
    ) {
        TypedQuery<Email> typedQuery = getFindAllQuery(owner, emailTo, emailFrom, status);
        int totalElements = typedQuery.getResultList().size();

        setQueryParameter(owner, emailTo, emailFrom, status, typedQuery);

        setPagination(pageable, typedQuery);

        List<Email> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, totalElements);
    }

    private void setPagination(Pageable pageable, TypedQuery<Email> typedQuery) {
        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    }

    private void setQueryParameter(String owner, String emailTo, String emailFrom, EmailStatusEnum status, TypedQuery<Email> typedQuery) {
        if (owner != null) {
            typedQuery.setParameter("owner", owner);
        }

        if (emailTo != null) {
            typedQuery.setParameter("emailTo", emailTo);
        }

        if (emailFrom != null) {
            typedQuery.setParameter("emailFrom", emailFrom);
        }

        if (status != null) {
            typedQuery.setParameter("status", status);
        }
    }

    private TypedQuery<Email> getFindAllQuery(String owner, String emailTo, String emailFrom, EmailStatusEnum status) {
        StringBuilder query = new StringBuilder(SELECT_ALL_EMAILS);

        if (owner != null) {
            query.append(" AND e.owner LIKE CONCAT('%', :owner, '%')");
        }

        if (emailTo != null) {
            query.append(" AND e.emailTo LIKE CONCAT('%', :emailTo, '%')");
        }

        if (emailFrom != null) {
            query.append(" AND e.emailFrom LIKE CONCAT('%', :emailFrom, '%')");
        }

        if (status != null) {
            query.append(" AND e.status = :status");
        }

        query.append(" ORDER BY e.id ASC");

        return em.createQuery(query.toString(), Email.class);
    }
}
