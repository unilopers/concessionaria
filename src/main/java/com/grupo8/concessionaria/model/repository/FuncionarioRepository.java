package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.Funcionario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface FuncionarioRepository extends CrudRepository<Funcionario, Long> {

    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
    Optional<Funcionario> findByCpf(String cpf);

    List<Funcionario> findByAtivoTrue();
}
