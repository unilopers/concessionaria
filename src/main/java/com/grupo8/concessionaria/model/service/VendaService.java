package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.Cliente;
import com.grupo8.concessionaria.model.entities.Funcionario;
import com.grupo8.concessionaria.model.entities.Venda;
import com.grupo8.concessionaria.model.repository.ClienteRepository;
import com.grupo8.concessionaria.model.repository.FuncionarioRepository;
import com.grupo8.concessionaria.model.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.*;
import java.util.*;

@Service
public class VendaService {
    @Autowired
    private VendaRepository vendaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private FuncionarioRepository funcionarioRepository;

    public Venda novaVenda(Venda venda) throws Exception {

        if (venda.getCliente() == null || venda.getCliente().getId() == null){
            throw new Exception("Cliente da venda é obrigatório.");
        }
        if (venda.getFuncionario() == null || venda.getFuncionario().getId() == null) {
            throw new Exception("Funcionário responsável pela venda é obrigatório.");
        }
        if (venda.getValorTotal() == null || venda.getValorTotal().compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Valor total da venda deve ser maior que zero.");
        }
        if (venda.getTipoPagamento() == null) {
            throw new Exception("Tipo de pagamento é obrigatório.");
        }

        Cliente cliente = clienteRepository.findById(venda.getCliente().getId()).orElseThrow(() -> new Exception("Cliente não encontrado para o id: " + venda.getCliente().getId()));
        Funcionario funcionario = funcionarioRepository.findById(venda.getFuncionario().getId()).orElseThrow(() -> new Exception("Funcionário não encontrado para o id: " + venda.getFuncionario().getId()));
        venda.setCliente(cliente);
        venda.setFuncionario(funcionario);

        if (venda.getDataVenda() == null) {
            venda.setDataVenda(LocalDateTime.now());
        }

        return vendaRepository.save(venda);
    }

    public List<Venda> listarVendas() {
        List<Venda> vendas = new ArrayList<>();
        vendaRepository.findAll().forEach(vendas::add);
        return vendas;
    }

    public Venda buscarPorId(Long id) throws Exception {
        return vendaRepository.findById(id).orElseThrow(() -> new Exception("Venda não encontrada para o id: " + id));
    }

    public List<Venda> listarPorCliente(Long idCliente) {
        return vendaRepository.findByClienteId(idCliente);
    }

    public List<Venda> listarPorFuncionario(Long idFuncionario) {
        return vendaRepository.findByFuncionarioId(idFuncionario);
    }

    public List<Venda> listarPorData(LocalDate data) {
        LocalDateTime inicio = data.atStartOfDay();
        LocalDateTime fim = data.atTime(LocalTime.MAX);
        return vendaRepository.findByDataVendaBetween(inicio, fim);
    }

    public void deletarVenda(Long id) throws Exception {
        Venda venda = buscarPorId(id);
        vendaRepository.delete(venda);
    }
}
