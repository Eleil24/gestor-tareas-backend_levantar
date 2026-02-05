package com.gestortask.demo.aplication.service;

import com.gestortask.demo.domain.model.Task;
import com.gestortask.demo.domain.repository.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task createTask(String title, String description) {
        Task task = new Task(title, description);
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task updateTask(Long id, String title, String description, boolean completed) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task no encontrada"));

        task.update(title, description, completed);

        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Page<Task> getTasks(
            int page,
            int size,
            String title,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Boolean completed

    ) {
        return taskRepository.findAll(
                page,
                size,
                title,
                startDate,
                endDate,
                completed
        );
    }
}
