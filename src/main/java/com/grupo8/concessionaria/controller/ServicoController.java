package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.Servico;
import com.grupo8.concessionaria.model.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicos")
public class ServicoController {

    private final ServicoService servicoService;

    @Autowired
    public ServicoController(ServicoService servicoService) {
        this.servicoService = servicoService;
    }

    @PostMapping("/novo")
    public ResponseEntity<?> criarServico(@RequestBody Servico servico) {
        try {
            Servico salvo = servicoService.novoServico(servico);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Servico>> listarServicos() {
        List<Servico> servicos = servicoService.listarServicos();
        return ResponseEntity.ok(servicos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Servico servico = servicoService.buscarPorId(id);
            return ResponseEntity.ok(servico);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Servico>> buscarPorDescricao(@RequestParam("descricao") String descricao) {
        List<Servico> servicos = servicoService.buscarPorDescricao(descricao);
        return ResponseEntity.ok(servicos);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> atualizarServico(@PathVariable Long id, @RequestBody Servico servico) {
        try {
            Servico atualizado = servicoService.atualizarServico(id, servico);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarServico(@PathVariable Long id) {
        try {
            servicoService.deletarServico(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}