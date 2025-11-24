package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.OrdemServico;
import com.grupo8.concessionaria.model.entities.StatusOS;
import com.grupo8.concessionaria.model.service.OrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ordens-servico")
public class OrdemServicoController {

    private final OrdemServicoService ordemServicoService;

    @Autowired
    public OrdemServicoController(OrdemServicoService ordemServicoService) {
        this.ordemServicoService = ordemServicoService;
    }

    @PostMapping("/novo/{idCliente}/{idFuncionario}")
    public ResponseEntity<?> criarOrdemServico(@PathVariable Long idCliente,
                                               @PathVariable Long idFuncionario,
                                               @RequestParam(value = "idVeiculo", required = false) Long idVeiculo,
                                               @RequestBody OrdemServico ordemServico) {
        try {
            OrdemServico salvo = ordemServicoService.novaOrdemServico(idCliente, idFuncionario, idVeiculo, ordemServico);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<OrdemServico>> listarTodas() {
        return ResponseEntity.ok(ordemServicoService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(ordemServicoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrdemServico>> listarPorStatus(@PathVariable StatusOS status) {
        return ResponseEntity.ok(ordemServicoService.listarPorStatus(status));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> atualizarDados(@PathVariable Long id,
                                            @RequestBody OrdemServico ordemServico) {
        try {
            OrdemServico atualizado = ordemServicoService.atualizarDadosBasicos(id, ordemServico);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<?> alterarStatus(@PathVariable Long id,
                                           @RequestParam("status") StatusOS status) {
        try {
            OrdemServico atualizado = ordemServicoService.alterarStatus(id, status);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            ordemServicoService.deletar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}