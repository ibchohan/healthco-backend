package com.appointment.repositories.specifications;

import com.appointment.common.enums.EventType;
import com.appointment.common.enums.ResourceType;
import com.appointment.common.utils.CommonUtils;
import com.appointment.entities.AuditLog;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;

public final class AuditLogSpecification {

    public static Specification<AuditLog> usernamePartialMatch(String username) {
        return (root, query, builder) -> {
            if (!CommonUtils.isNullOrEmpty(username)) {
                Predicate lowerCasePredicate = builder.like(root.get("username"), "%" + username.toLowerCase() + "%");
                Predicate upperCasePredicate = builder.like(root.get("username"), "%" + username.toUpperCase() + "%");
                return builder.or(lowerCasePredicate, upperCasePredicate);
            }
            return null;
        };
    }

    public static Specification<AuditLog> eventTypeEquals(EventType eventType) {
        return (root, query, builder) -> {
            if (!CommonUtils.isNull(eventType)) {
                return builder.equal(root.get("eventType"), eventType.getId());
            }
            return null;
        };
    }

    public static Specification<AuditLog> resourceTypeEquals(ResourceType resourceType) {
        return (root, query, builder) -> {
            if (!CommonUtils.isNull(resourceType)) {
                return builder.equal(root.get("resourceType"), resourceType.getId());
            }
            return null;
        };
    }

    public static Specification<AuditLog> dateRangeWithin(Long startDateEpoch , Long endDateEpoch) {
        return (root, query, builder) -> {
            if (!CommonUtils.isNull(startDateEpoch) && !CommonUtils.isNull(endDateEpoch)) {

                Predicate greaterThanOrEqualToPredicate =
                        builder.greaterThanOrEqualTo( root.get("createdAt"), Instant.ofEpochMilli(startDateEpoch));
                Predicate lessThanPredicate =
                        builder.lessThan(root.get("createdAt"), Instant.ofEpochMilli(endDateEpoch));

                return builder.and(greaterThanOrEqualToPredicate, lessThanPredicate);

            }
            return null;
        };
    }

}