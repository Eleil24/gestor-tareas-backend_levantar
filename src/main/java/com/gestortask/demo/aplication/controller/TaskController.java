package com.gestortask.demo.aplication.controller;


import com.gestortask.demo.aplication.dto.CreateTaskRequest;
import com.gestortask.demo.aplication.dto.TaskResponse;
import com.gestortask.demo.aplication.dto.UpdateTaskRequest;
import com.gestortask.demo.aplication.service.TaskService;
import com.gestortask.demo.domain.model.Task;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:5173",
        originPatterns = "https://gestor-de-tareas-front-levantar.vercel.app/")
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public TaskResponse create(@Valid @RequestBody CreateTaskRequest request) {
        Task task = taskService.createTask(
                request.getTitle(),
                request.getDescription()
        );

        return toResponse(task);
    }

    @GetMapping
    public Page<TaskResponse> getTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size,
            @RequestParam(required = false) String title,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate,
            @RequestParam(required = false) Boolean completed
    ) {
        return taskService.getTasks(
                        page,
                        size,
                        title,
                        startDate,
                        endDate,
                        completed
                )
                .map(this::toResponse);
    }


    @PutMapping("/{id}")
    public TaskResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateTaskRequest request
    ) {
        Task task = taskService.updateTask(
                id,
                request.getTitle(),
                request.getDescription(),
                request.isCompleted()
        );

        return toResponse(task);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.deleteTask(id);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted(),
                task.getCreatedAt()
        );
    }
}
