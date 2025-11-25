package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.MovimentacaoFinanceira;
import com.grupo8.concessionaria.model.entities.OrigemMovimentacao;
import com.grupo8.concessionaria.model.entities.TipoMovimentacao;
import com.grupo8.concessionaria.model.service.MovimentacaoFinanceiraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/movimentacoes-financeiras")
public class MovimentacaoFinanceiraController {

    private final MovimentacaoFinanceiraService movimentacaoService;

    @Autowired
    public MovimentacaoFinanceiraController(MovimentacaoFinanceiraService movimentacaoService) {
        this.movimentacaoService = movimentacaoService;
    }


    @PostMapping("/novo")
    public ResponseEntity<?> criarMovimentacao(@RequestParam(value = "idVenda", required = false) Long idVenda,
                                               @RequestParam(value = "idOs", required = false) Long idOs,
                                               @RequestBody MovimentacaoFinanceira movimentacao) {
        try {
            MovimentacaoFinanceira salva = movimentacaoService.novaMovimentacao(movimentacao, idVenda, idOs);
            return ResponseEntity.status(201).body(salva);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping
    public ResponseEntity<List<MovimentacaoFinanceira>> listarTodas() {
        return ResponseEntity.ok(movimentacaoService.listarTodas());
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(movimentacaoService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<MovimentacaoFinanceira>> listarPorTipo(@PathVariable TipoMovimentacao tipo) {
        return ResponseEntity.ok(movimentacaoService.listarPorTipo(tipo));
    }


    @GetMapping("/origem/{origem}")
    public ResponseEntity<List<MovimentacaoFinanceira>> listarPorOrigem(@PathVariable OrigemMovimentacao origem) {
        return ResponseEntity.ok(movimentacaoService.listarPorOrigem(origem));
    }

    @GetMapping("/periodo")
    public ResponseEntity<List<MovimentacaoFinanceira>> listarPorPeriodo(@RequestParam String inicio,
                                                                         @RequestParam String fim) {
        LocalDateTime dataInicio = LocalDateTime.parse(inicio);
        LocalDateTime dataFim = LocalDateTime.parse(fim);
        return ResponseEntity.ok(movimentacaoService.listarPorPeriodo(dataInicio, dataFim));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id,
                                       @RequestBody MovimentacaoFinanceira movimentacao) {
        try {
            MovimentacaoFinanceira atualizada = movimentacaoService.atualizarMovimentacao(id, movimentacao);
            return ResponseEntity.ok(atualizada);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            movimentacaoService.deletar(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}