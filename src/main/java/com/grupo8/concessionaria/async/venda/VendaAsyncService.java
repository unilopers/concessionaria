package com.grupo8.concessionaria.async.venda;

import com.grupo8.concessionaria.model.entities.MovimentacaoFinanceira;
import com.grupo8.concessionaria.model.entities.OrigemMovimentacao;
import com.grupo8.concessionaria.model.entities.TipoMovimentacao;
import com.grupo8.concessionaria.model.entities.Venda;
import com.grupo8.concessionaria.model.service.MovimentacaoFinanceiraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class VendaAsyncService {
    private static final Logger logger = LoggerFactory.getLogger(VendaAsyncService.class);

    private final MovimentacaoFinanceiraService movimentacaoFinanceiraService;

    public VendaAsyncService(MovimentacaoFinanceiraService movimentacaoFinanceiraService) {
        this.movimentacaoFinanceiraService = movimentacaoFinanceiraService;
    }

    @Async
    public void processarMovimentacaoVenda(Venda venda) {
        if (venda == null || venda.getId() == null) {
            logger.warn("Venda nula ou sem ID ao tentar processar movimentação async.");
            return;
        }

        try {
            MovimentacaoFinanceira mov = new MovimentacaoFinanceira();
            mov.setValor(venda.getValorTotal());
            mov.setTipo(TipoMovimentacao.ENTRADA);
            mov.setOrigem(OrigemMovimentacao.VENDA);
            mov.setDescricao("Movimentação de venda de " + venda.getValorTotal() + " para venda ID " + venda.getId());
            movimentacaoFinanceiraService.novaMovimentacao(mov, venda.getId(), null);
            logger.info("Movimentação financeira assíncrona criada para venda ID {}", venda.getId());
        } catch (Exception e) {
            logger.error("Erro ao processar movimentação financeira assíncrona para venda ID {}: {}", venda.getId(), e.getMessage(), e);
        }
    }
}
