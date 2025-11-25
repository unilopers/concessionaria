package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.ItemOrdemServico;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemOrdemServicoRepository extends CrudRepository<ItemOrdemServico, Long> {

    List<ItemOrdemServico> findByOrdemServicoId(Long idOs);
}