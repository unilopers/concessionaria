package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.Cliente;
import com.grupo8.concessionaria.model.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @PostMapping("/novo")
    public ResponseEntity<?> criarCliente(@RequestBody Cliente cliente) {
        try {
            Cliente salvo = clienteService.novoCliente(cliente);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> listarClientes() {
        List<Cliente> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    // GET /clientes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Cliente cliente = clienteService.buscarPorId(id);
            return ResponseEntity.ok(cliente);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Cliente>> buscarPorNome(@RequestParam("nome") String nome) {
        List<Cliente> clientes = clienteService.buscarPorNome(nome);
        return ResponseEntity.ok(clientes);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> atualizarCliente(@PathVariable Long id, @RequestBody Cliente cliente) {
        try {
            Cliente atualizado = clienteService.atualizarCliente(id, cliente);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable Long id) {
        try {
            clienteService.deletarCliente(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
