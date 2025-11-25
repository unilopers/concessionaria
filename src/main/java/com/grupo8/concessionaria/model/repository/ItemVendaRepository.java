package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.ItemVenda;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemVendaRepository extends CrudRepository<ItemVenda, Long> {

    List<ItemVenda> findByVendaId(Long idVenda);
}
