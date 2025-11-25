package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.ItemOrdemServico;
import com.grupo8.concessionaria.model.entities.OrdemServico;
import com.grupo8.concessionaria.model.entities.Servico;
import com.grupo8.concessionaria.model.repository.ItemOrdemServicoRepository;
import com.grupo8.concessionaria.model.repository.OrdemServicoRepository;
import com.grupo8.concessionaria.model.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemOrdemServicoService {

    @Autowired
    private ItemOrdemServicoRepository itemRepository;

    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    public ItemOrdemServico novoItem(Long idOs, Long idServico, ItemOrdemServico dados) throws Exception {

        if (dados.getQuantidade() == null || dados.getQuantidade() <= 0) {
            throw new Exception("A quantidade deve ser maior que zero");
        }

        OrdemServico os = ordemServicoRepository.findById(idOs)
                .orElseThrow(() -> new Exception("Ordem de serviço não encontrada"));

        Servico servico = servicoRepository.findById(idServico)
                .orElseThrow(() -> new Exception("Serviço não encontrado"));

        dados.setOrdemServico(os);
        dados.setServico(servico);


        if (dados.getValorUnitario() == null) {
            BigDecimal valorBase = servico.getValorMaoObraBase();
            if (valorBase == null) {
                throw new Exception("Valor de mão de obra do serviço não definido");
            }
            dados.setValorUnitario(valorBase);
        }

        return itemRepository.save(dados);
    }

    public List<ItemOrdemServico> listarTodos() {
        List<ItemOrdemServico> lista = new ArrayList<>();
        itemRepository.findAll().forEach(lista::add);
        return lista;
    }

    public ItemOrdemServico buscarPorId(Long id) throws Exception {
        return itemRepository.findById(id)
                .orElseThrow(() -> new Exception("Item de OS não encontrado para o id: " + id));
    }

    public List<ItemOrdemServico> listarPorOrdemServico(Long idOs) {
        return itemRepository.findByOrdemServicoId(idOs);
    }

    public ItemOrdemServico atualizarItem(Long id, ItemOrdemServico atualizados) throws Exception {
        ItemOrdemServico existente = buscarPorId(id);

        if (atualizados.getQuantidade() != null && atualizados.getQuantidade() > 0) {
            existente.setQuantidade(atualizados.getQuantidade());
        }

        if (atualizados.getValorUnitario() != null &&
                atualizados.getValorUnitario().compareTo(BigDecimal.ZERO) > 0) {
            existente.setValorUnitario(atualizados.getValorUnitario());
        }


        if (atualizados.getServico() != null && atualizados.getServico().getId() != null) {
            Servico servico = servicoRepository.findById(atualizados.getServico().getId())
                    .orElseThrow(() -> new Exception("Novo serviço não encontrado"));
            existente.setServico(servico);
        }

        return itemRepository.save(existente);
    }

    public void deletarItem(Long id) throws Exception {
        ItemOrdemServico item = buscarPorId(id);
        itemRepository.delete(item);
    }
}