package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.Cliente;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import java.util.*;


@Repository
public interface ClienteRepository extends CrudRepository<Cliente, Long> {
    List<Cliente> findByNomeContainingIgnoreCase(String nome);
    Optional<Cliente> findByCpf(String cpf);
}
