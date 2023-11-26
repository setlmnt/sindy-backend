package com.ifba.educampo.repository.associate;

import com.ifba.educampo.annotation.Log;
import com.ifba.educampo.model.entity.associate.Associate;
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
                "SELECT a FROM Associate a "
        );

        TypedQuery<Associate> typedQuery = filterQueryByNameAndCpfAndUnionCard(query, name, cpf, unionCard);

        setPagination(pageable, typedQuery);

        List<Associate> resultList = typedQuery.getResultList();
        return new PageImpl<>(resultList, pageable, resultList.size());
    }

    private TypedQuery<Associate> filterQueryByNameAndCpfAndUnionCard(
            StringBuilder query,
            String name,
            String cpf,
            Long unionCard
    ) {
        String prefix = "WHERE";

        if (name != null) {
            query.append(prefix).append(" LOWER(a.name) LIKE LOWER(:name)");
            prefix = "AND";
        }

        if (cpf != null) {
            query.append(prefix).append(" LOWER(a.cpf) LIKE LOWER(:cpf)");
            prefix = "AND";
        }

        if (unionCard != null) {
            query.append(prefix).append(" CAST(a.unionCard AS string) LIKE :unionCard");
        }

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
}
