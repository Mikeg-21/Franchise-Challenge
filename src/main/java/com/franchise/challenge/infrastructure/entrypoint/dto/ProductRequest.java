package com.franchise.challenge.infrastructure.entrypoint.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ProductRequest(@NotBlank String name, @Min(0) int stock) {}