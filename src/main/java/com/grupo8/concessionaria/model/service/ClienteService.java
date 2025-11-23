package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.Cliente;
import com.grupo8.concessionaria.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClienteService {
    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente novoCliente(Cliente cliente) throws Exception {

        if (cliente.getNome() == null || cliente.getNome().isBlank()) {
            throw new Exception("O nome do cliente é obrigatório");
        }

        if (cliente.getCpf() == null || cliente.getCpf().isBlank()) {
            throw new Exception("O CPF do cliente é obrigatório");
        }

        if (cliente.getCpf().length() != 11) {
            throw new Exception("O CPF deve conter 11 dígitos numéricos");
        }

        Optional<Cliente> clienteExistente = clienteRepository.findByCpf(cliente.getCpf());
        if (clienteExistente.isPresent()) {
            throw new Exception("Ja existe um cliente cadastrado com esse CPF");
        }

        return clienteRepository.save(cliente);
    }

    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clienteRepository.findAll().forEach(clientes::add);
        return clientes;
    }

    public Cliente buscarPorId(Long id) throws Exception {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new Exception("Cliente não encontrado para o id: " + id));
    }

    public List<Cliente> buscarPorNome(String nome) {
        return clienteRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Cliente atualizarCliente(Long id, Cliente dadosAtualizados) throws Exception {
        Cliente clienteExistente = buscarPorId(id);

        clienteExistente.setNome(dadosAtualizados.getNome());
        clienteExistente.setTelefone(dadosAtualizados.getTelefone());
        clienteExistente.setEmail(dadosAtualizados.getEmail());
        clienteExistente.setEndereco(dadosAtualizados.getEndereco());

        return clienteRepository.save(clienteExistente);
    }

    public void deletarCliente(Long id) throws Exception {
        Cliente cliente = buscarPorId(id);
        clienteRepository.delete(cliente);
    }
}
