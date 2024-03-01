package br.com.sindy.domain.repository.spec;

import br.com.sindy.domain.entity.MonthlyFee;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MonthlyFeeSpecs {
    public static Specification<MonthlyFee> filter(LocalDate initialDate, LocalDate finalDate) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get("deleted"), false));

            setDatePredicates(initialDate, finalDate, root, builder, predicates);

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<MonthlyFee> filter(Long associateId, LocalDate initialDate, LocalDate finalDate) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(builder.equal(root.get("deleted"), false));

            predicates.add(builder.equal(root.get("associate").get("id"), associateId));
            setDatePredicates(initialDate, finalDate, root, builder, predicates);

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

    private static void setDatePredicates(LocalDate initialDate, LocalDate finalDate, Root<MonthlyFee> root, CriteriaBuilder builder, List<Predicate> predicates) {
        if (Objects.nonNull(initialDate)) {
            predicates.add(builder.equal(root.get("initialDate"), initialDate));
        }

        if (Objects.nonNull(finalDate)) {
            predicates.add(builder.equal(root.get("finalDate"), finalDate));
        }
    }
}
