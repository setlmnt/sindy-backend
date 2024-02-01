package com.ifba.educampo.repository.impl;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.dto.email.EmailResponseDto;
import com.ifba.educampo.enums.EmailStatusEnum;
import com.ifba.educampo.repository.CustomEmailRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log
public class CustomEmailRepositoryImpl implements CustomEmailRepository {
    private static final String SELECT_ALL_EMAILS = "SELECT new com.ifba.educampo.dto.email.EmailResponseDto(ch.id, ch.sender, ch.recipients, ch.message, ch.subject, ch.status)" +
            "FROM CommunicationHistory ch INNER JOIN FETCH ch.recipients cr WHERE ch.deleted = false";

    @PersistenceContext
    private EntityManager em;

    private void setPagination(Pageable pageable, TypedQuery<?> typedQuery) {
        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    }
    @Override
    public Page<EmailResponseDto> findAllWithFilter(String sender, String recipient, EmailStatusEnum status, Pageable pageable) {
        StringBuilder query = new StringBuilder(SELECT_ALL_EMAILS);

        TypedQuery<EmailResponseDto> typedQuery = filterEmail(query, sender, recipient, status);
        int totalElements = typedQuery.getResultList().size();

        setPagination(pageable, typedQuery);

        List<EmailResponseDto> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, totalElements);
    }

    private TypedQuery<EmailResponseDto> filterEmail(StringBuilder query, String sender, String recipient, EmailStatusEnum status) {
        if (!StringUtils.isEmpty(sender)) {
            query.append(" AND ch.sender = :sender");
        }

        if (!StringUtils.isEmpty(recipient)) {
            query.append(" AND cr.recipient = :recipient");
        }

        if (status != null) {
            query.append(" AND ch.status = :status");
        }

        TypedQuery<EmailResponseDto> typedQuery = em.createQuery(query.toString(), EmailResponseDto.class);

        if (!StringUtils.isEmpty(sender)) {
            typedQuery.setParameter("sender", sender);
        }

        if (!StringUtils.isEmpty(recipient)) {
            typedQuery.setParameter("recipient", recipient);
        }

        if (status != null) {
            typedQuery.setParameter("status", status);
        }

        return typedQuery;
    }
}
