package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.Funcionario;
import com.grupo8.concessionaria.model.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FuncionarioService {
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Funcionario novoFuncionario(Funcionario funcionario) throws Exception {

        if (funcionario.getNome() == null || funcionario.getNome().isBlank()) {
            throw new Exception("O nome do funcionário é obrigatório");
        }

        if (funcionario.getCpf() == null || funcionario.getCpf().isBlank()) {
            throw new Exception("O CPF do funcionário é obrigatório");
        }

        if (funcionario.getCpf().length() != 11) {
            throw new Exception("O CPF do funcionário deve conter 11 dígitos");
        }

        if (funcionario.getCargo() == null || funcionario.getCargo().isBlank()) {
            throw new Exception("O cargo do funcionário é obrigatório");
        }

        if (funcionario.getDataAdmissao() == null) {
            throw new Exception("A data de admissão é obrigatória");
        }

        Optional<Funcionario> existente = funcionarioRepository.findByCpf(funcionario.getCpf());
        if (existente.isPresent()) {
            throw new Exception("Ja existe um funcionário cadastrado com esse CPF");
        }

        if (funcionario.getAtivo() == null) {
            funcionario.setAtivo(true);
        }

        return funcionarioRepository.save(funcionario);
    }

    public List<Funcionario> listarFuncionarios() {
        List<Funcionario> funcionarios = new ArrayList<>();
        funcionarioRepository.findAll().forEach(funcionarios::add);
        return funcionarios;
    }

    public List<Funcionario> listarAtivos() {
        return funcionarioRepository.findByAtivoTrue();
    }

    public Funcionario buscarPorId(Long id) throws Exception {
        return funcionarioRepository.findById(id).orElseThrow(() -> new Exception("Funcionário não encontrado para o id: " + id));
    }

    public List<Funcionario> buscarPorNome(String nome) {
        return funcionarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Funcionario atualizarFuncionario(Long id, Funcionario dadosAtualizados) throws Exception {
        Funcionario funcionario = buscarPorId(id);

        funcionario.setNome(dadosAtualizados.getNome());
        funcionario.setCargo(dadosAtualizados.getCargo());
        funcionario.setSalario(dadosAtualizados.getSalario());
        funcionario.setDataAdmissao(dadosAtualizados.getDataAdmissao());
        funcionario.setAtivo(dadosAtualizados.getAtivo() != null ? dadosAtualizados.getAtivo() : funcionario.getAtivo());

        return funcionarioRepository.save(funcionario);
    }

    public void deletarFuncionario(Long id) throws Exception {
        Funcionario funcionario = buscarPorId(id);
        funcionarioRepository.delete(funcionario);
    }

    public Funcionario desativarFuncionario(Long id) throws Exception {
        Funcionario funcionario = buscarPorId(id);
        funcionario.setAtivo(false);
        return funcionarioRepository.save(funcionario);
    }
}
