package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.Modelo;
import com.grupo8.concessionaria.model.service.ModeloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/modelos")
public class ModeloController {

    private final ModeloService modeloService;

    @Autowired
    public ModeloController(ModeloService modeloService) {
        this.modeloService = modeloService;
    }

    @PostMapping("/novo")
    public ResponseEntity<?> criarModelo(@RequestBody Modelo modelo) {
        try {
            Modelo salvo = modeloService.novoModelo(modelo);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Modelo>> listarModelos() {
        List<Modelo> modelos = modeloService.listarModelos();
        return ResponseEntity.ok(modelos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Modelo modelo = modeloService.buscarPorId(id);
            return ResponseEntity.ok(modelo);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Modelo>> buscarPorMarca(@RequestParam("marca") String marca) {
        List<Modelo> modelos = modeloService.buscarPorMarca(marca);
        return ResponseEntity.ok(modelos);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> atualizarModelo(@PathVariable Long id, @RequestBody Modelo modelo) {
        try {
            Modelo atualizado = modeloService.atualizarModelo(id, modelo);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarModelo(@PathVariable Long id) {
        try {
            modeloService.deletarModelo(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}