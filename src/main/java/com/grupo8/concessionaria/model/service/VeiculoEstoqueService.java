package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.VeiculoEstoque;
import com.grupo8.concessionaria.model.entities.Modelo;
import com.grupo8.concessionaria.model.entities.StatusVeiculo;
import com.grupo8.concessionaria.model.repository.VeiculoEstoqueRepository;
import com.grupo8.concessionaria.model.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class VeiculoEstoqueService {

    @Autowired
    private VeiculoEstoqueRepository veiculoRepository;

    @Autowired
    private ModeloRepository modeloRepository;

    public VeiculoEstoque novoVeiculo(Long idModelo, VeiculoEstoque veiculo) throws Exception {

        if (veiculo.getChassi() == null || veiculo.getChassi().isBlank()) {
            throw new Exception("Chassi é obrigatório");
        }

        Optional<VeiculoEstoque> existente = veiculoRepository.findByChassi(veiculo.getChassi());
        if (existente.isPresent()) {
            throw new Exception("Já existe veículo com esse chassi");
        }

        Modelo modelo = modeloRepository.findById(idModelo)
                .orElseThrow(() -> new Exception("Modelo não encontrado"));

        veiculo.setModelo(modelo);
        veiculo.setStatusVeiculo(StatusVeiculo.ESTOQUE);

        return veiculoRepository.save(veiculo);
    }

    public List<VeiculoEstoque> listarTodos() {
        List<VeiculoEstoque> lista = new ArrayList<>();
        veiculoRepository.findAll().forEach(lista::add);
        return lista;
    }

    public VeiculoEstoque buscarPorId(Long id) throws Exception {
        return veiculoRepository.findById(id)
                .orElseThrow(() -> new Exception("Veículo não encontrado"));
    }

    public List<VeiculoEstoque> listarPorStatus(StatusVeiculo status) {
        return veiculoRepository.findByStatusVeiculo(status);
    }

    public VeiculoEstoque alterarStatus(Long id, StatusVeiculo status) throws Exception {
        VeiculoEstoque veiculo = buscarPorId(id);
        veiculo.setStatusVeiculo(status);
        return veiculoRepository.save(veiculo);
    }

    public void deletar(Long id) throws Exception {
        VeiculoEstoque veiculo = buscarPorId(id);
        veiculoRepository.delete(veiculo);
    }
}
