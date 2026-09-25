package com.example.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record NewTask(@NotBlank @Size(max = 200) String title) {
}
