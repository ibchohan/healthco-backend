package com.appointment.repositories.specifications;

import com.appointment.common.constants.Constants;
import com.appointment.common.utils.CommonUtils;
import com.appointment.entities.User;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.regex.Pattern;


public final class UserSpecification {

    public static Specification<User> searchQueryPartialMatches(String searchQuery) {
        return (root, query, builder) -> {
            if (!CommonUtils.isNullOrEmpty(searchQuery)) {
                Predicate fullNamePredicate = fullNamePartialMatches(searchQuery).toPredicate(root, query, builder);
                Predicate usernamePredicate = usernamePartialMatches(searchQuery).toPredicate(root, query, builder);
                Predicate emailPredicate = emailPartialMatches(searchQuery).toPredicate(root, query, builder);
                Predicate uuidPredicate = uuidPartialMatches(searchQuery).toPredicate(root, query, builder);
                return builder.or(fullNamePredicate, usernamePredicate, emailPredicate, uuidPredicate);
            }
            return null;
        };
    }

    public static Specification<User> uuidPartialMatches(String uuid) {
        return (root, query, builder) -> {
            if (!CommonUtils.isNullOrEmpty(uuid)) {
                String uuidTrimmed = uuid.replaceAll("(?i)" + Pattern.quote(Constants.USER_UUID_PREFIX), "");
                return builder.like(root.get("uuid"), "%" + uuidTrimmed + "%");
            }
            return null;
        };
    }

    public static Specification<User> fullNamePartialMatches(String searchQuery) {
        return (root, query, builder) -> {
            if (!CommonUtils.isNullOrEmpty(searchQuery)) {
                return builder.like(builder.lower(root.get("fullName")), "%" + searchQuery.toLowerCase() + "%");
            }
            return null;
        };
    }

    public static Specification<User> usernamePartialMatches(String searchQuery) {
        return (root, query, builder) -> {
            if (!CommonUtils.isNullOrEmpty(searchQuery)) {
                return builder.like(builder.lower(root.get("username")), "%" + searchQuery.toLowerCase() + "%");
            }
            return null;
        };
    }

    public static Specification<User> emailPartialMatches(String searchQuery) {
        return (root, query, builder) -> {
            if (!CommonUtils.isNullOrEmpty(searchQuery)) {
                return builder.like(builder.lower(root.get("email")), "%" + searchQuery.toLowerCase() + "%");
            }
            return null;
        };
    }

    public static Specification<User> isDeactivatedEquals(Boolean isDeactivated) {
        return (root, query, builder) -> {
            if (isDeactivated == null) return null;
            return builder.equal(root.get("isDeactivated"), isDeactivated);
        };
    }

}
