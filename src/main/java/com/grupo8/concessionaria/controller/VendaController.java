package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.Venda;
import com.grupo8.concessionaria.model.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/vendas")
public class VendaController {
    private VendaService vendaService;

    @Autowired
    public VendaController(VendaService vendaService) {
        this.vendaService = vendaService;
    }

    @PostMapping("/novo")
    public ResponseEntity<?> criarVenda(@RequestBody Venda venda) {
        try {
            Venda salva = vendaService.novaVenda(venda);
            return ResponseEntity.status(201).body(salva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Venda>> listarVendas() {
        List<Venda> vendas = vendaService.listarVendas();
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Venda venda = vendaService.buscarPorId(id);
            return ResponseEntity.ok(venda);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/cliente/{idCliente}")
    public ResponseEntity<List<Venda>> listarPorCliente(@PathVariable Long idCliente) {
        List<Venda> vendas = vendaService.listarPorCliente(idCliente);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/funcionario/{idFuncionario}")
    public ResponseEntity<List<Venda>> listarPorFuncionario(@PathVariable Long idFuncionario) {
        List<Venda> vendas = vendaService.listarPorFuncionario(idFuncionario);
        return ResponseEntity.ok(vendas);
    }

    @GetMapping("/data")
    public ResponseEntity<List<Venda>> listarPorData(@RequestParam("data") String data) {
        LocalDate date = LocalDate.parse(data);
        List<Venda> vendas = vendaService.listarPorData(date);
        return ResponseEntity.ok(vendas);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarVenda(@PathVariable Long id) {
        try {
            vendaService.deletarVenda(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
