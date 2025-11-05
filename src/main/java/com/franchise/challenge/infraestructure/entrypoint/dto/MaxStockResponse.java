package com.franchise.challenge.infraestructure.entrypoint.dto;

public record MaxStockResponse(String branchName, String productName, int stock) {}