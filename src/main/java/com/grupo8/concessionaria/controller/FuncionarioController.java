package com.grupo8.concessionaria.controller;

import com.grupo8.concessionaria.model.entities.Funcionario;
import com.grupo8.concessionaria.model.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {
    private FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/novo")
    public ResponseEntity<?> criarFuncionario(@RequestBody Funcionario funcionario) {
        try {
            Funcionario salvo = funcionarioService.novoFuncionario(funcionario);
            return ResponseEntity.status(201).body(salvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Funcionario>> listarFuncionarios() {
        List<Funcionario> funcionarios = funcionarioService.listarFuncionarios();
        return ResponseEntity.ok(funcionarios);
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<Funcionario>> listarAtivos() {
        List<Funcionario> ativos = funcionarioService.listarAtivos();
        return ResponseEntity.ok(ativos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPorId(@PathVariable Long id) {
        try {
            Funcionario funcionario = funcionarioService.buscarPorId(id);
            return ResponseEntity.ok(funcionario);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @GetMapping("/busca")
    public ResponseEntity<List<Funcionario>> buscarPorNome(@RequestParam("nome") String nome) {
        List<Funcionario> funcionarios = funcionarioService.buscarPorNome(nome);
        return ResponseEntity.ok(funcionarios);
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionario) {
        try {
            Funcionario atualizado = funcionarioService.atualizarFuncionario(id, funcionario);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/desativar")
    public ResponseEntity<?> desativarFuncionario(@PathVariable Long id) {
        try {
            Funcionario desativado = funcionarioService.desativarFuncionario(id);
            return ResponseEntity.ok(desativado);
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarFuncionario(@PathVariable Long id) {
        try {
            funcionarioService.deletarFuncionario(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
