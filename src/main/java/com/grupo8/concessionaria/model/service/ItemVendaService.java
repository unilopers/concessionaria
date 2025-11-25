package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.ItemVenda;
import com.grupo8.concessionaria.model.entities.VeiculoEstoque;
import com.grupo8.concessionaria.model.entities.Venda;
import com.grupo8.concessionaria.model.repository.ItemVendaRepository;
import com.grupo8.concessionaria.model.repository.VeiculoEstoqueRepository;
import com.grupo8.concessionaria.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemVendaService {

    @Autowired
    private ItemVendaRepository itemVendaRepository;

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private VeiculoEstoqueRepository veiculoEstoqueRepository;

    public ItemVenda novoItemVenda(ItemVenda item) throws Exception {

        if (item.getVenda() == null || item.getVenda().getId() == null) {
            throw new Exception("Venda é obrigatória para o item de venda.");
        }

        if (item.getVeiculo() == null || item.getVeiculo().getId() == null) {
            throw new Exception("Veículo é obrigatório para o item de venda.");
        }

        if (item.getValorUnitario() == null ||
                item.getValorUnitario().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("O valor unitário deve ser maior que zero.");
        }

        // validar existência da venda
        Venda venda = vendaRepository.findById(item.getVenda().getId())
                .orElseThrow(() ->
                        new Exception("Venda não encontrada para o id: " + item.getVenda().getId())
                );

        // validar existência do veículo
        VeiculoEstoque veiculo = veiculoEstoqueRepository.findById(item.getVeiculo().getId())
                .orElseThrow(() ->
                        new Exception("Veículo não encontrado para o id: " + item.getVeiculo().getId())
                );

        item.setVenda(venda);
        item.setVeiculo(veiculo);

        // aqui daria pra mudar status do veículo para VENDIDO, se quiser
        // ex.: veiculo.setStatusVeiculo(StatusVeiculo.VENDIDO); veiculoEstoqueRepository.save(veiculo);

        return itemVendaRepository.save(item);
    }

    public List<ItemVenda> listarTodos() {
        List<ItemVenda> itens = new ArrayList<>();
        itemVendaRepository.findAll().forEach(itens::add);
        return itens;
    }

    public ItemVenda buscarPorId(Long id) throws Exception {
        return itemVendaRepository.findById(id)
                .orElseThrow(() -> new Exception("Item de venda não encontrado para o id: " + id));
    }

    public List<ItemVenda> listarPorVenda(Long idVenda) {
        return itemVendaRepository.findByVendaId(idVenda);
    }

    public void deletarItem(Long id) throws Exception {
        ItemVenda item = buscarPorId(id);
        itemVendaRepository.delete(item);
    }
}
