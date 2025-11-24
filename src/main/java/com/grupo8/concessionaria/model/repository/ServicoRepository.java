package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.Servico;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoRepository extends CrudRepository<Servico, Long> {

    List<Servico> findByDescricaoContainingIgnoreCase(String descricao);
}