package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.*;
import com.grupo8.concessionaria.model.repository.MovimentacaoFinanceiraRepository;
import com.grupo8.concessionaria.model.repository.OrdemServicoRepository;
import com.grupo8.concessionaria.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovimentacaoFinanceiraService {

    @Autowired
    private MovimentacaoFinanceiraRepository movimentacaoRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    public MovimentacaoFinanceira novaMovimentacao(MovimentacaoFinanceira dados,
                                                   Long idVendaOpcional,
                                                   Long idOsOpcional) throws Exception {

        if (dados.getTipo() == null) {
            throw new Exception("O tipo da movimentação é obrigatório (ENTRADA ou SAIDA)");
        }

        if (dados.getOrigem() == null) {
            throw new Exception("A origem da movimentação é obrigatória (VENDA, ORDEM_SERVICO ou AJUSTE)");
        }

        if (dados.getValor() == null || dados.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor deve ser maior que zero");
        }

        // vincula com venda ou OS se informado
        if (idVendaOpcional != null) {
            Venda venda = vendaRepository.findById(idVendaOpcional)
                    .orElseThrow(() -> new Exception("Venda não encontrada"));
            dados.setVenda(venda);
            dados.setOrigem(OrigemMovimentacao.VENDA);
        }

        if (idOsOpcional != null) {
            OrdemServico os = ordemServicoRepository.findById(idOsOpcional)
                    .orElseThrow(() -> new Exception("Ordem de serviço não encontrada"));
            dados.setOrdemServico(os);
            dados.setOrigem(OrigemMovimentacao.ORDEM_SERVICO);
        }

        if (dados.getDataMovimentacao() == null) {
            dados.setDataMovimentacao(LocalDateTime.now());
        }

        return movimentacaoRepository.save(dados);
    }

    public List<MovimentacaoFinanceira> listarTodas() {
        List<MovimentacaoFinanceira> lista = new ArrayList<>();
        movimentacaoRepository.findAll().forEach(lista::add);
        return lista;
    }

    public MovimentacaoFinanceira buscarPorId(Long id) throws Exception {
        return movimentacaoRepository.findById(id)
                .orElseThrow(() -> new Exception("Movimentação não encontrada para o id: " + id));
    }

    public List<MovimentacaoFinanceira> listarPorTipo(TipoMovimentacao tipo) {
        return movimentacaoRepository.findByTipo(tipo);
    }

    public List<MovimentacaoFinanceira> listarPorOrigem(OrigemMovimentacao origem) {
        return movimentacaoRepository.findByOrigem(origem);
    }

    public List<MovimentacaoFinanceira> listarPorPeriodo(LocalDateTime inicio, LocalDateTime fim) {
        return movimentacaoRepository.findByDataMovimentacaoBetween(inicio, fim);
    }

    public MovimentacaoFinanceira atualizarMovimentacao(Long id, MovimentacaoFinanceira dados) throws Exception {
        MovimentacaoFinanceira existente = buscarPorId(id);

        if (dados.getDescricao() != null) {
            existente.setDescricao(dados.getDescricao());
        }

        if (dados.getValor() != null && dados.getValor().compareTo(BigDecimal.ZERO) > 0) {
            existente.setValor(dados.getValor());
        }

        if (dados.getTipo() != null) {
            existente.setTipo(dados.getTipo());
        }

        if (dados.getOrigem() != null) {
            existente.setOrigem(dados.getOrigem());
        }

        return movimentacaoRepository.save(existente);
    }

    public void deletar(Long id) throws Exception {
        MovimentacaoFinanceira mov = buscarPorId(id);
        movimentacaoRepository.delete(mov);
    }
}
