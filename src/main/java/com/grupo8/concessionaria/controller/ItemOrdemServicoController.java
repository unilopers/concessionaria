package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.ItemOrdemServico;
import com.grupo8.concessionaria.model.service.ItemOrdemServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-os")
public class ItemOrdemServicoController {

    private final ItemOrdemServicoService itemService;

    @Autowired
    public ItemOrdemServicoController(ItemOrdemServicoService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/novo/{idOs}/{idServico}")
    public ResponseEntity<?> criarItem(@PathVariable Long idOs,
                                       @PathVariable Long idServico,
                                       @RequestBody ItemOrdemServico item) {
        try {
            ItemOrdemServico salvo = itemService.novoItem(idOs, idServico, item);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<ItemOrdemServico>> listarTodos() {
        return ResponseEntity.ok(itemService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(itemService.buscarPorId(id));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/os/{idOs}")
    public ResponseEntity<List<ItemOrdemServico>> listarPorOrdemServico(@PathVariable Long idOs) {
        return ResponseEntity.ok(itemService.listarPorOrdemServico(idOs));
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> atualizarItem(@PathVariable Long id,
                                           @RequestBody ItemOrdemServico item) {
        try {
            ItemOrdemServico atualizado = itemService.atualizarItem(id, item);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarItem(@PathVariable Long id) {
        try {
            itemService.deletarItem(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}