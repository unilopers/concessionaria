package com.grupo8.concessionaria.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimentacoes_financeiras")
public class MovimentacaoFinanceira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_movimentacao")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private TipoMovimentacao tipo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrigemMovimentacao origem;

    @ManyToOne
    @JoinColumn(name = "id_venda")
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "id_os")
    private OrdemServico ordemServico;

    @Column(name = "data_movimentacao", nullable = false)
    private LocalDateTime dataMovimentacao = LocalDateTime.now();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(length = 200)
    private String descricao;

    public MovimentacaoFinanceira() {
    }

    // GETTERS E SETTERS

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public TipoMovimentacao getTipo() { return tipo; }

    public void setTipo(TipoMovimentacao tipo) { this.tipo = tipo; }

    public OrigemMovimentacao getOrigem() { return origem; }

    public void setOrigem(OrigemMovimentacao origem) { this.origem = origem; }

    public Venda getVenda() { return venda; }

    public void setVenda(Venda venda) { this.venda = venda; }

    public OrdemServico getOrdemServico() { return ordemServico; }

    public void setOrdemServico(OrdemServico ordemServico) { this.ordemServico = ordemServico; }

    public LocalDateTime getDataMovimentacao() { return dataMovimentacao; }

    public void setDataMovimentacao(LocalDateTime dataMovimentacao) {
        this.dataMovimentacao = dataMovimentacao;
    }

    public BigDecimal getValor() { return valor; }

    public void setValor(BigDecimal valor) { this.valor = valor; }

    public String getDescricao() { return descricao; }

    public void setDescricao(String descricao) { this.descricao = descricao; }
}