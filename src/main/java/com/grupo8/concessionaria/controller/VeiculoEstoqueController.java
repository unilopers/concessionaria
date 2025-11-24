package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.VeiculoEstoque;
import com.grupo8.concessionaria.model.entities.StatusVeiculo;
import com.grupo8.concessionaria.model.service.VeiculoEstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoEstoqueController {

    private final VeiculoEstoqueService veiculoService;

    @Autowired
    public VeiculoEstoqueController(VeiculoEstoqueService veiculoService) {
        this.veiculoService = veiculoService;
    }

    @PostMapping("/novo/{idModelo}")
    public ResponseEntity<?> criar(@PathVariable Long idModelo, @RequestBody VeiculoEstoque veiculo) {
        try {
            VeiculoEstoque salvo = veiculoService.novoVeiculo(idModelo, veiculo);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<VeiculoEstoque>> listar() {
        return ResponseEntity.ok(veiculoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(veiculoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<VeiculoEstoque>> listarPorStatus(@PathVariable StatusVeiculo status) {
        return ResponseEntity.ok(veiculoService.listarPorStatus(status));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable Long id,
                                           @RequestParam StatusVeiculo status) {
        try {
            VeiculoEstoque v = veiculoService.alterarStatus(id, status);
            return ResponseEntity.ok(v);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
