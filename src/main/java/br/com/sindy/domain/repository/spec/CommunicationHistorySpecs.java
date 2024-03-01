package br.com.sindy.domain.repository.spec;

import br.com.sindy.domain.entity.email.CommunicationHistory;
import br.com.sindy.domain.enums.EmailStatusEnum;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CommunicationHistorySpecs {
    public static Specification<CommunicationHistory> filter(String sender, String recipient, EmailStatusEnum status) {
        return (root, query, builder) -> {
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

            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
