package com.franchise.challenge.infrastructure.entrypoint.dto;

public record MaxStockResponse(String branchName, String productName, int stock) {}