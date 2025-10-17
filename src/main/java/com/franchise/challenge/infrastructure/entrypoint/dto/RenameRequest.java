package com.franchise.challenge.infrastructure.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;

public record RenameRequest(@NotBlank String newName) {}