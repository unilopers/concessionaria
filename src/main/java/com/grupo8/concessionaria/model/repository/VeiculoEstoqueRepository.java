package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.VeiculoEstoque;
import com.grupo8.concessionaria.model.entities.StatusVeiculo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VeiculoEstoqueRepository extends CrudRepository<VeiculoEstoque, Long> {

    Optional<VeiculoEstoque> findByChassi(String chassi);

    List<VeiculoEstoque> findByStatusVeiculo(StatusVeiculo statusVeiculo);
}
