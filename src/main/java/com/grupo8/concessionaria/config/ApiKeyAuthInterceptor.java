package com.grupo8.concessionaria.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Component
public class ApiKeyAuthInterceptor implements HandlerInterceptor {

    private final ApiKeyConfig apiKeyConfig;

    public ApiKeyAuthInterceptor(ApiKeyConfig apiKeyConfig) {
        this.apiKeyConfig = apiKeyConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Libera preflight de CORS sem exigir autenticacao.
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        String receivedApiKey = request.getHeader(apiKeyConfig.getHeaderName());

        // Falha padronizada para chave ausente.
        if (receivedApiKey == null || receivedApiKey.isBlank()) {
            writeErrorResponse(
                    response,
                    request,
                    HttpStatus.UNAUTHORIZED,
                    "API_KEY_MISSING",
                    "API Key ausente. Informe a chave no header x-api-key."
            );
            return false;
        }

        // Falha padronizada para chave invalida.
        if (!apiKeyConfig.getApiKey().equals(receivedApiKey)) {
            writeErrorResponse(
                    response,
                    request,
                    HttpStatus.UNAUTHORIZED,
                    "API_KEY_INVALID",
                    "API Key invalida."
            );
            return false;
        }

        return true;
    }

    private void writeErrorResponse(
            HttpServletResponse response,
            HttpServletRequest request,
            HttpStatus status,
            String code,
            String message
    ) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ApiErrorResponse error = new ApiErrorResponse(
                code,
                message,
                status.value(),
                Instant.now(),
                request.getRequestURI()
        );

        response.getWriter().write(toJson(error));
    }

    // Mantem a serializacao independente de bibliotecas extras.
    private String toJson(ApiErrorResponse error) {
        return String.format(
                "{\"code\":\"%s\",\"message\":\"%s\",\"status\":%d,\"timestamp\":\"%s\",\"path\":\"%s\"}",
                escapeJson(error.code()),
                escapeJson(error.message()),
                error.status(),
                DateTimeFormatter.ISO_INSTANT.format(error.timestamp()),
                escapeJson(error.path())
        );
    }

    // Escapa caracteres essenciais para JSON valido.
    private String escapeJson(String value) {
        return value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
