package com.gestortask.demo.aplication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateTaskRequest {
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100)
    private String title;

    @Size(max = 255)
    private String description;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
