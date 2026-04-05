package com.teamdev.group_up.specification;

import com.teamdev.group_up.dto.SearchGroupRequest;
import com.teamdev.group_up.entity.Group;
import com.teamdev.group_up.entity.GroupTag;
import com.teamdev.group_up.enums.Branch;
import com.teamdev.group_up.enums.GroupStatus;
import com.teamdev.group_up.enums.Year;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.time.Instant;
import java.util.List;

public class GroupSpecification {

    public static Specification<Group> withFilters(SearchGroupRequest request) {
        return Specification
                .where(excludeCancelled())
                .and(hasKeyword(request.keyword()))
                .and(hasTagIds(request.tagIds()))
                .and(hasDateFrom(request.dateFrom()))
                .and(hasDateTo(request.dateTo()))
                .and(hasCreatorYear(request.creatorYear()))
                .and(hasCreatorBranch(request.creatorBranch()));
    }

    private static Specification<Group> hasKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;
            String pattern = "%" + keyword.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern)
            );
        };
    }

    private static Specification<Group> hasTagIds(List<Long> tagIds) {
        return (root, query, cb) -> {
            if (tagIds == null || tagIds.isEmpty()) return null;
            Join<Group, GroupTag> groupTagJoin = root.join("groupTags", JoinType.INNER);
            query.distinct(true);
            return groupTagJoin.get("id").get("tagId").in(tagIds);
        };
    }

    private static Specification<Group> hasDateFrom(Instant dateFrom) {
        return (root, query, cb) -> {
            if (dateFrom == null) return null;
            return cb.greaterThanOrEqualTo(root.get("activityDateTime"), dateFrom);
        };
    }

    private static Specification<Group> hasDateTo(Instant dateTo) {
        return (root, query, cb) -> {
            if (dateTo == null) return null;
            return cb.lessThanOrEqualTo(root.get("activityDateTime"), dateTo);
        };
    }

    private static Specification<Group> hasCreatorYear(Year creatorYear) {
        return (root, query, cb) -> {
            if (creatorYear == null) return null;
            return cb.equal(root.get("creator").get("year"), creatorYear);
        };
    }

    private static Specification<Group> hasCreatorBranch(Branch creatorBranch) {
        return (root, query, cb) -> {
            if (creatorBranch == null) return null;
            return cb.equal(root.get("creator").get("branch"), creatorBranch);
        };
    }

    private static Specification<Group> excludeCancelled() {
        return (root, query, cb) ->
                cb.notEqual(root.get("status"), GroupStatus.CANCELLED);
    }
}