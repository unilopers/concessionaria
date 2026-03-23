package com.grupo8.concessionaria;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ApiKeyIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Value("${auth.api-key}")
    private String apiKey;

    @Value("${auth.api-header:x-api-key}")
    private String apiHeader;

    @BeforeEach
    void setUp() {
        // Usa o contexto real para testar interceptor e mapeamentos juntos.
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void deveNegarRotaProtegidaSemApiKey() throws Exception {
        // Sem chave -> deve retornar 401 com codigo de ausente.
        mockMvc.perform(get("/clientes"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("API_KEY_MISSING"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.path").value("/clientes"));
    }

    @Test
    void deveNegarRotaProtegidaComApiKeyInvalida() throws Exception {
        // Chave invalida -> deve retornar 401 com codigo de invalida.
        mockMvc.perform(get("/clientes")
                        .header(apiHeader, "chave-invalida"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value("API_KEY_INVALID"))
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.path").value("/clientes"));
    }

    @Test
    void devePermitirRotaProtegidaComApiKeyValida() throws Exception {
        // Chave correta -> acesso permitido na rota protegida.
        mockMvc.perform(get("/clientes")
                        .header(apiHeader, apiKey))
                .andExpect(status().isOk());
    }

    @Test
    void devePermitirRotaPublicaSemApiKey() throws Exception {
        // Rota publica deve funcionar mesmo sem header de autenticacao.
        mockMvc.perform(get("/public/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ok"));
    }
}
