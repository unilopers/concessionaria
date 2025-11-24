package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.Servico;
import com.grupo8.concessionaria.model.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicoService {

    @Autowired
    private ServicoRepository servicoRepository;

    public Servico novoServico(Servico servico) throws Exception {

        if (servico.getDescricao() == null || servico.getDescricao().isBlank()) {
            throw new Exception("A descrição do serviço é obrigatória");
        }

        if (servico.getValorMaoObraBase() == null) {
            throw new Exception("O valor da mão de obra é obrigatório");
        }

        if (servico.getValorMaoObraBase().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor da mão de obra deve ser maior que zero");
        }

        return servicoRepository.save(servico);
    }

    public List<Servico> listarServicos() {
        List<Servico> servicos = new ArrayList<>();
        servicoRepository.findAll().forEach(servicos::add);
        return servicos;
    }

    public Servico buscarPorId(Long id) throws Exception {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new Exception("Serviço não encontrado para o id: " + id));
    }

    public List<Servico> buscarPorDescricao(String descricao) {
        return servicoRepository.findByDescricaoContainingIgnoreCase(descricao);
    }

    public Servico atualizarServico(Long id, Servico dadosAtualizados) throws Exception {
        Servico servicoExistente = buscarPorId(id);

        servicoExistente.setDescricao(dadosAtualizados.getDescricao());
        servicoExistente.setValorMaoObraBase(dadosAtualizados.getValorMaoObraBase());

        return servicoRepository.save(servicoExistente);
    }

    public void deletarServico(Long id) throws Exception {
        Servico servico = buscarPorId(id);
        servicoRepository.delete(servico);
    }
}