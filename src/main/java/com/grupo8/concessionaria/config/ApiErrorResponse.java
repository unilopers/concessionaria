package com.grupo8.concessionaria.config;

import java.time.Instant;

// Contrato padrao para respostas de erro da API.
public record ApiErrorResponse(
        String code,
        String message,
        int status,
        Instant timestamp,
        String path
) {
}
