package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.OrdemServico;
import com.grupo8.concessionaria.model.entities.StatusOS;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdemServicoRepository extends CrudRepository<OrdemServico, Long> {

    List<OrdemServico> findByStatusOS(StatusOS statusOS);
}