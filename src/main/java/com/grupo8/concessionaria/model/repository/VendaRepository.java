package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.Venda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface VendaRepository extends CrudRepository<Venda, Long> {
    List<Venda> findByClienteId(Long idCliente);

    List<Venda> findByFuncionarioId(Long idFuncionario);

    List<Venda> findByDataVendaBetween(LocalDateTime inicio, LocalDateTime fim);
}
