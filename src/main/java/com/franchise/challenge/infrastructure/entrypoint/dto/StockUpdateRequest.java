package com.franchise.challenge.infrastructure.entrypoint.dto;

import jakarta.validation.constraints.Min;

public record StockUpdateRequest(@Min(0) int stock) {}