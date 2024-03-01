package br.com.sindy.domain.repository.spec;

import br.com.sindy.domain.entity.associate.Associate;
import br.com.sindy.domain.enums.PeriodEnum;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AssociateSpecs {
    public static Specification<Associate> filterFindAll(String filter) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (!StringUtils.isEmpty(filter)) {
                predicates.add(builder.or(
                        builder.like(builder.lower(root.get("name")), "%" + filter.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("cpf")), "%" + filter.toLowerCase() + "%"),
                        builder.like(builder.lower(root.get("unionCard").as(String.class)), filter.toLowerCase() + "%")
                ));
            }

            predicates.add(builder.equal(root.get("deleted"), false));

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }

    public static Specification<Associate> findAllBirthdayAssociates(PeriodEnum period) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (period == PeriodEnum.DAY) {
                predicates.add(builder.equal(
                        builder.function("TO_CHAR", String.class, root.get("birthAt"), builder.literal("YYYY-mm-dd")),
                        builder.function("TO_CHAR", String.class, builder.currentDate(), builder.literal("YYYY-mm-dd"))
                ));
            } else if (period == PeriodEnum.WEEK) {
                predicates.add(builder.equal(
                        builder.function("DATE_PART", String.class, builder.literal("WEEK"), root.get("birthAt")),
                        builder.function("DATE_PART", String.class, builder.literal("WEEK"), builder.currentDate())
                ));
            } else {
                predicates.add(builder.equal(
                        builder.function("TO_CHAR", String.class, root.get("birthAt"), builder.literal("YYYY-mm")),
                        builder.function("TO_CHAR", String.class, builder.currentDate(), builder.literal("YYYY-mm"))
                ));
            }

            predicates.add(builder.equal(root.get("deleted"), false));

            query.orderBy(builder.asc(root.get("birthAt")));

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
