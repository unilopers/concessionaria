package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.Modelo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends CrudRepository<Modelo, Long> {

    List<Modelo> findByMarcaContainingIgnoreCase(String marca);

    List<Modelo> findByNomeContainingIgnoreCase(String nome);
}
