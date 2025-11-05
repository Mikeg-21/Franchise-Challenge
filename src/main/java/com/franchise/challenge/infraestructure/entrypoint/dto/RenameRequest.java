package com.franchise.challenge.infraestructure.entrypoint.dto;

import jakarta.validation.constraints.NotBlank;

public record RenameRequest(@NotBlank String newName) {}