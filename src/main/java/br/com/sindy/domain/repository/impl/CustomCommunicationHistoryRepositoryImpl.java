package br.com.sindy.domain.repository.impl;

import br.com.sindy.domain.entity.email.CommunicationHistory;
import br.com.sindy.domain.enums.EmailStatusEnum;
import br.com.sindy.domain.repository.CustomCommunicationHistoryRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class CustomCommunicationHistoryRepositoryImpl implements CustomCommunicationHistoryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<CommunicationHistory> findAllWithFilter(
            String sender,
            String recipient,
            EmailStatusEnum status,
            Pageable pageable
    ) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CommunicationHistory> query = builder.createQuery(CommunicationHistory.class);
        Root<CommunicationHistory> root = query.from(CommunicationHistory.class);

        root.fetch("recipients");

        List<Predicate> predicates = getPredicatesForFindAllWithFilter(sender, recipient, status, builder, root);
        query.where(predicates.toArray(Predicate[]::new));

        List<Order> orders = getOrders(pageable, builder, root);
        query.orderBy(orders);

        TypedQuery<CommunicationHistory> typedQuery = entityManager.createQuery(query);

        Long totalElements = getCommunicationHistoryTotalElements(builder, sender, recipient, status);

        setPagination(typedQuery, pageable);

        List<CommunicationHistory> communicationHistories = typedQuery.getResultList();
        return new PageImpl<>(communicationHistories, pageable, totalElements);
    }

    private Long getCommunicationHistoryTotalElements(CriteriaBuilder builder, String sender, String recipient, EmailStatusEnum status) {
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<CommunicationHistory> root = query.from(CommunicationHistory.class);

        query.select(builder.count(root));

        List<Predicate> predicates = getPredicatesForFindAllWithFilter(sender, recipient, status, builder, root);
        query.where(predicates.toArray(Predicate[]::new));

        return entityManager.createQuery(query).getSingleResult();
    }

    private List<Predicate> getPredicatesForFindAllWithFilter(String sender, String recipient, EmailStatusEnum status, CriteriaBuilder builder, Root<CommunicationHistory> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (StringUtils.isNotEmpty(sender)) {
            predicates.add(builder.or(
                    builder.like(builder.lower(root.get("senderName")), "%" + sender.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("senderEmail")), "%" + sender.toLowerCase() + "%")
            ));
        }

        if (StringUtils.isNotEmpty(recipient)) {
            predicates.add(builder.or(
                    builder.like(builder.lower(root.get("recipients").get("name")), "%" + recipient.toLowerCase() + "%"),
                    builder.like(builder.lower(root.get("recipients").get("email")), "%" + recipient.toLowerCase() + "%")
            ));
        }

        if (Objects.nonNull(status)) {
            predicates.add(builder.equal(root.get("status"), status));
        }
        return predicates;
    }

    private void setPagination(TypedQuery<?> typedQuery, Pageable pageable) {
        typedQuery.setMaxResults(pageable.getPageSize());
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
    }

    private List<Order> getOrders(Pageable pageable, CriteriaBuilder criteriaBuilder, Root<?> root) {
        List<Order> orders = new ArrayList<>();
        pageable.getSort().forEach(order -> {
            String property = order.getProperty();
            Path<?> path = getPath(root, property);

            if (order.getDirection().isAscending()) {
                orders.add(criteriaBuilder.asc(path));
            } else {
                orders.add(criteriaBuilder.desc(path));
            }
        });
        return orders;
    }

    private Path<?> getPath(Root<?> root, String property) {
        if (property.contains(".")) {
            String[] propertyParts = property.split("\\."); // root.child
            String propertyRoot = propertyParts[0]; // root
            String propertyChild = propertyParts[1]; // child
            return root.get(propertyRoot).get(propertyChild);
        }

        return root.get(property);
    }
}
