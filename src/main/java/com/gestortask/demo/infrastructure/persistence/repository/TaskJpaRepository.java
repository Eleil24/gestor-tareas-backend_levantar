package com.gestortask.demo.infrastructure.persistence.repository;

import com.gestortask.demo.infrastructure.persistence.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface TaskJpaRepository extends JpaRepository<TaskEntity, Long>,
        JpaSpecificationExecutor<TaskEntity> {
}
