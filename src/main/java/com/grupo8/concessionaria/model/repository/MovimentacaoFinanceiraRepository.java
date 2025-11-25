package com.grupo8.concessionaria.model.repository;

import com.grupo8.concessionaria.model.entities.MovimentacaoFinanceira;
import com.grupo8.concessionaria.model.entities.OrigemMovimentacao;
import com.grupo8.concessionaria.model.entities.TipoMovimentacao;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MovimentacaoFinanceiraRepository extends CrudRepository<MovimentacaoFinanceira, Long> {

    List<MovimentacaoFinanceira> findByTipo(TipoMovimentacao tipo);

    List<MovimentacaoFinanceira> findByOrigem(OrigemMovimentacao origem);

    List<MovimentacaoFinanceira> findByDataMovimentacaoBetween(LocalDateTime inicio, LocalDateTime fim);
}
