package com.gestortask.demo.domain.repository;

import com.gestortask.demo.domain.model.Task;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskRepository {

    Task save(Task task);

    List<Task> findAll();

    Page<Task> findAll(
            int page,
            int size,
            String title,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Boolean completed
    );

    Optional<Task> findById(Long id);

    void deleteById(Long id);

}
