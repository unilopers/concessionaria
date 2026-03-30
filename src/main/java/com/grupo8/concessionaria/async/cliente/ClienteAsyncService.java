package com.grupo8.concessionaria.async.cliente;

import com.grupo8.concessionaria.model.entities.Cliente;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class ClienteAsyncService {

    @Async
    public void processarPosCadastroCliente(Cliente cliente) {
        try {
            System.out.println("[ASYNC] Iniciando processamento do cliente: " + cliente.getNome());

            Thread.sleep(3000);

            System.out.println("[ASYNC] Auditoria registrada para o cliente ID: " + cliente.getId());
            System.out.println("[ASYNC] Processamento finalizado para o cliente: " + cliente.getNome());
        } catch (Exception e) {
            System.err.println("[ASYNC] Erro ao processar cliente em background: " + e.getMessage());
        }
    }
}