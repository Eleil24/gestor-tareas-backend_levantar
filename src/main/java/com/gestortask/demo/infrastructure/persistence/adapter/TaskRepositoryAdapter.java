package com.gestortask.demo.infrastructure.persistence.adapter;

import com.gestortask.demo.domain.model.Task;
import com.gestortask.demo.domain.repository.TaskRepository;
import com.gestortask.demo.infrastructure.persistence.entity.TaskEntity;
import com.gestortask.demo.infrastructure.persistence.repository.TaskJpaRepository;
import com.gestortask.demo.infrastructure.persistence.specification.TaskSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TaskRepositoryAdapter implements TaskRepository {
    private final TaskJpaRepository jpaRepository;

    public TaskRepositoryAdapter(TaskJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Task save(Task task) {
        TaskEntity entity = toEntity(task);
        TaskEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Task> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Page<Task> findAll(int page, int size, String title, LocalDateTime startDate, LocalDateTime endDate, Boolean completed) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<TaskEntity> spec =
                Specification.where(TaskSpecifications.titleContains(title))
                        .and(TaskSpecifications.createdAfter(startDate))
                        .and(TaskSpecifications.createdBefore(endDate))
                        .and(TaskSpecifications.isCompleted(completed));

        return jpaRepository.findAll(spec, pageable)
                .map(this::toDomain);
    }

    @Override
    public Optional<Task> findById(Long id) {
        return jpaRepository.findById(id)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    private TaskEntity toEntity(Task task) {
        return new TaskEntity(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt()
        );
    }

    private Task toDomain(TaskEntity entity) {
        return new Task(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.isCompleted(),
                entity.getCreatedAt()
        );
    }
}
