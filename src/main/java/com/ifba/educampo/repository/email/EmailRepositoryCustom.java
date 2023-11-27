package com.ifba.educampo.repository.email;

import com.ifba.educampo.model.entity.Email;
import com.ifba.educampo.model.enums.StatusEmail;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmailRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    public Page<Email> findAllWithFilterAndDeletedFalse(
            String owner,
            String emailTo,
            String emailFrom,
            StatusEmail status,
            Pageable pageable
    ) {
        TypedQuery<Email> typedQuery = getFindAllQuery(owner, emailTo, emailFrom, status);

        setQueryParameter(owner, emailTo, emailFrom, status, typedQuery);

        setPagination(pageable, typedQuery);

        List<Email> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    private void setPagination(Pageable pageable, TypedQuery<Email> typedQuery) {
        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    }

    private void setQueryParameter(String owner, String emailTo, String emailFrom, StatusEmail status, TypedQuery<Email> typedQuery) {
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

    private TypedQuery<Email> getFindAllQuery(String owner, String emailTo, String emailFrom, StatusEmail status) {
        StringBuilder query = new StringBuilder("SELECT e FROM Email e WHERE e.deleted = false");

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
