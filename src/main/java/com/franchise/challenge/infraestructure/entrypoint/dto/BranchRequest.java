package com.franchise.challenge.infraestructure.entrypoint.dto;


import jakarta.validation.constraints.NotBlank;

public record BranchRequest(@NotBlank String name) {}