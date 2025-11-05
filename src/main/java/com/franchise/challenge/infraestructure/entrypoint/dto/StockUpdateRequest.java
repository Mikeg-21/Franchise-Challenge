package com.franchise.challenge.infraestructure.entrypoint.dto;

import jakarta.validation.constraints.Min;

public record StockUpdateRequest(@Min(0) int stock) {}