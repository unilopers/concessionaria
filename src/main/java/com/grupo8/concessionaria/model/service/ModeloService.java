package com.grupo8.concessionaria.model.service;

import com.grupo8.concessionaria.model.entities.Modelo;
import com.grupo8.concessionaria.model.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModeloService {

    @Autowired
    private ModeloRepository modeloRepository;

    public Modelo novoModelo(Modelo modelo) throws Exception {
        if (modelo.getNome() == null || modelo.getNome().isBlank()) {
            throw new Exception("O nome do modelo é obrigatório");
        }

        if (modelo.getMarca() == null || modelo.getMarca().isBlank()) {
            throw new Exception("A marca é obrigatória");
        }

        return modeloRepository.save(modelo);
    }

    public List<Modelo> listarModelos() {
        List<Modelo> modelos = new ArrayList<>();
        modeloRepository.findAll().forEach(modelos::add);
        return modelos;
    }

    public Modelo buscarPorId(Long id) throws Exception {
        return modeloRepository.findById(id)
                .orElseThrow(() -> new Exception("Modelo não encontrado para o id: " + id));
    }

    public List<Modelo> buscarPorMarca(String marca) {
        return modeloRepository.findByMarcaContainingIgnoreCase(marca);
    }

    public Modelo atualizarModelo(Long id, Modelo dadosAtualizados) throws Exception {
        Modelo modeloExistente = buscarPorId(id);

        modeloExistente.setNome(dadosAtualizados.getNome());
        modeloExistente.setMarca(dadosAtualizados.getMarca());
        modeloExistente.setAnoLancamento(dadosAtualizados.getAnoLancamento());
        modeloExistente.setTipoCombustivel(dadosAtualizados.getTipoCombustivel());
        modeloExistente.setCategoria(dadosAtualizados.getCategoria());

        return modeloRepository.save(modeloExistente);
    }

    public void deletarModelo(Long id) throws Exception {
        Modelo modelo = buscarPorId(id);
        modeloRepository.delete(modelo);
    }
}