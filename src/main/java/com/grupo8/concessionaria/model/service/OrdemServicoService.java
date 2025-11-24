package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.*;
import com.grupo8.concessionaria.model.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrdemServicoService {

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private VeiculoEstoqueRepository veiculoEstoqueRepository;

    public OrdemServico novaOrdemServico(Long idCliente,
                                         Long idFuncionario,
                                         Long idVeiculoOpcional,
                                         OrdemServico dados) throws Exception {

        var cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new Exception("Cliente não encontrado"));

        var funcionario = funcionarioRepository.findById(idFuncionario)
                .orElseThrow(() -> new Exception("Funcionário responsável não encontrado"));

        VeiculoEstoque veiculo = null;
        if (idVeiculoOpcional != null) {
            veiculo = veiculoEstoqueRepository.findById(idVeiculoOpcional)
                    .orElseThrow(() -> new Exception("Veículo não encontrado"));
        }

        dados.setCliente(cliente);
        dados.setFuncionarioResponsavel(funcionario);
        dados.setVeiculo(veiculo);

        if (dados.getStatusOS() == null) {
            dados.setStatusOS(StatusOS.ABERTA);
        }

        if (dados.getDataAbertura() == null) {
            dados.setDataAbertura(LocalDateTime.now());
        }

        return ordemServicoRepository.save(dados);
    }

    public List<OrdemServico> listarTodas() {
        List<OrdemServico> lista = new ArrayList<>();
        ordemServicoRepository.findAll().forEach(lista::add);
        return lista;
    }

    public OrdemServico buscarPorId(Long id) throws Exception {
        return ordemServicoRepository.findById(id)
                .orElseThrow(() -> new Exception("Ordem de serviço não encontrada para o id: " + id));
    }

    public List<OrdemServico> listarPorStatus(StatusOS status) {
        return ordemServicoRepository.findByStatusOS(status);
    }

    public OrdemServico atualizarDadosBasicos(Long id, OrdemServico dados) throws Exception {
        OrdemServico existente = buscarPorId(id);

        if (dados.getValorTotal() != null &&
                dados.getValorTotal().compareTo(BigDecimal.ZERO) >= 0) {
            existente.setValorTotal(dados.getValorTotal());
        }

        if (dados.getStatusOS() != null) {
            existente.setStatusOS(dados.getStatusOS());
            if (dados.getStatusOS() == StatusOS.FECHADA && existente.getDataFechamento() == null) {
                existente.setDataFechamento(LocalDateTime.now());
            }
        }

        if (dados.getDataFechamento() != null) {
            existente.setDataFechamento(dados.getDataFechamento());
        }

        return ordemServicoRepository.save(existente);
    }

    public OrdemServico alterarStatus(Long id, StatusOS status) throws Exception {
        OrdemServico existente = buscarPorId(id);
        existente.setStatusOS(status);

        if (status == StatusOS.FECHADA) {
            existente.setDataFechamento(LocalDateTime.now());
        }

        return ordemServicoRepository.save(existente);
    }

    public void deletar(Long id) throws Exception {
        OrdemServico os = buscarPorId(id);
        ordemServicoRepository.delete(os);
    }
}