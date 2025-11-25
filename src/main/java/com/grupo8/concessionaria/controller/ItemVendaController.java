package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.ItemVenda;
import com.grupo8.concessionaria.model.service.ItemVendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-venda")
public class ItemVendaController {

    private final ItemVendaService itemVendaService;

    @Autowired
    public ItemVendaController(ItemVendaService itemVendaService) {
        this.itemVendaService = itemVendaService;
    }

    // POST /itens-venda/novo
    @PostMapping("/novo")
    public ResponseEntity<?> criarItemVenda(@RequestBody ItemVenda itemVenda) {
        try {
            ItemVenda salvo = itemVendaService.novoItemVenda(itemVenda);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET /itens-venda
    @GetMapping
    public ResponseEntity<List<ItemVenda>> listarTodos() {
        List<ItemVenda> itens = itemVendaService.listarTodos();
        return ResponseEntity.ok(itens);
    }

    // GET /itens-venda/{id}
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            ItemVenda item = itemVendaService.buscarPorId(id);
            return ResponseEntity.ok(item);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // GET /itens-venda/venda/{idVenda}
    @GetMapping("/venda/{idVenda}")
    public ResponseEntity<List<ItemVenda>> listarPorVenda(@PathVariable Long idVenda) {
        List<ItemVenda> itens = itemVendaService.listarPorVenda(idVenda);
        return ResponseEntity.ok(itens);
    }

    // DELETE /itens-venda/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarItem(@PathVariable Long id) {
        try {
            itemVendaService.deletarItem(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
