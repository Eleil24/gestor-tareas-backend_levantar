package com.gestortask.demo.infrastructure.persistence.specification;

import com.gestortask.demo.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TaskSpecifications {
    public static Specification<TaskEntity> titleContains(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(
                    cb.lower(root.get("title")),
                    "%" + title.toLowerCase() + "%"
            );
        };
    }

    public static Specification<TaskEntity> isCompleted(Boolean completed) {
        return (root, query, cb) -> {
            if (completed == null) {
                return cb.conjunction(); // no filtra
            }
            return cb.equal(root.get("completed"), completed);
        };
    }


    public static Specification<TaskEntity> createdAfter(LocalDateTime start) {
        return (root, query, cb) -> {
            if (start == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("createdAt"), start);
        };
    }

    public static Specification<TaskEntity> createdBefore(LocalDateTime end) {
        return (root, query, cb) -> {
            if (end == null) {
                return cb.conjunction();
            }
            return cb.lessThanOrEqualTo(root.get("createdAt"), end);
        };
    }
}
