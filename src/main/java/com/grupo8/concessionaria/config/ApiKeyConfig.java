package com.grupo8.concessionaria.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiKeyConfig {

    // Chave esperada para autenticar chamadas em rotas protegidas.
    @Value("${auth.api-key}")
    private String apiKey;

    // Header de transporte da chave (padrao: x-api-key).
    @Value("${auth.api-header:x-api-key}")
    private String headerName;

    public String getApiKey() {
        return apiKey;
    }

    public String getHeaderName() {
        return headerName;
    }
}
