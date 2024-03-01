package br.com.sindy.domain.repository.impl;

import br.com.sindy.domain.repository.CustomAssociateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomAssociateRepositoryImpl implements CustomAssociateRepository {
    private static final String a = """
            SELECT DISTINCT
                a.id
            FROM Associate a
                LEFT JOIN MonthlyFee mf ON a.id = mf.associate.id
            WHERE a.deleted = false
                AND a.isPaid = true
                AND mf.deleted = false
                AND CURRENT_DATE > (
                    SELECT
                        mf2.finalDate
                    FROM MonthlyFee mf2
                        JOIN Associate a2 ON mf2.associate.id = a2.id
                    WHERE a2.id = a.id and mf2.deleted = false
                        AND a2.deleted = false
                    ORDER BY mf2.finalDate DESC
                    LIMIT 1
            )
            """;

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Long> findAllAssociatesWithExpiredMonthlyFee() {
        TypedQuery<Long> query = em.createQuery(a, Long.class);
        return query.getResultList();
    }
}
