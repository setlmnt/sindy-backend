package br.com.sindy.domain.repository.impl;

import br.com.sindy.core.annotation.Log;
import br.com.sindy.domain.dto.email.EmailResponseDto;
import br.com.sindy.domain.entity.email.CommunicationHistory;
import br.com.sindy.domain.enums.EmailStatusEnum;
import br.com.sindy.domain.mapper.email.EmailMapper;
import br.com.sindy.domain.repository.CustomEmailRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log
@RequiredArgsConstructor
public class CustomEmailRepositoryImpl implements CustomEmailRepository {
    private static final String SELECT_ALL_EMAILS = "SELECT ch FROM CommunicationHistory ch JOIN FETCH ch.recipients cr WHERE ch.deleted = false";

    private final EmailMapper emailMapper;

    @PersistenceContext
    private EntityManager em;

    private void setPagination(Pageable pageable, TypedQuery<?> typedQuery) {
        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    }

    @Override
    public Page<EmailResponseDto> findAllWithFilter(String sender, String recipient, EmailStatusEnum status, Pageable pageable) {
        StringBuilder query = new StringBuilder(SELECT_ALL_EMAILS);

        TypedQuery<CommunicationHistory> typedQuery = filterEmail(query, sender, recipient, status);
        int totalElements = typedQuery.getResultList().size();

        setPagination(pageable, typedQuery);

        List<CommunicationHistory> resultList = typedQuery.getResultList();

        List<EmailResponseDto> emailResponses = resultList.stream()
                .map(emailMapper::toResponseDto)
                .toList();

        return new PageImpl<>(emailResponses, pageable, totalElements);
    }

    private TypedQuery<CommunicationHistory> filterEmail(StringBuilder query, String sender, String recipient, EmailStatusEnum status) {
        if (!StringUtils.isEmpty(sender)) {
            query.append(" AND (ch.senderName LIKE :sender OR ch.senderEmail LIKE :sender)");
        }

        if (!StringUtils.isEmpty(recipient)) {
            query.append(" AND (cr.email LIKE :recipient OR cr.name LIKE :recipient)");
        }

        if (status != null) {
            query.append(" AND ch.status = :status");
        }

        TypedQuery<CommunicationHistory> typedQuery = em.createQuery(query.toString(), CommunicationHistory.class);

        if (!StringUtils.isEmpty(sender)) {
            typedQuery.setParameter("sender", "%" + sender + "%");
        }

        if (!StringUtils.isEmpty(recipient)) {
            typedQuery.setParameter("recipient", "%" + recipient + "%");
        }

        if (status != null) {
            typedQuery.setParameter("status", status);
        }

        return typedQuery;
    }
}
