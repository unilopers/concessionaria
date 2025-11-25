package com.grupo8.concessionaria.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "itens_venda",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_item_venda_veiculo",
                        columnNames = "id_veiculo"
                )
        }
)
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_item_venda")
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_venda", nullable = false)
    private Venda venda;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_veiculo", nullable = false)
    private VeiculoEstoque veiculo;

    @Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorUnitario;

    public ItemVenda() {
    }

    public ItemVenda(Venda venda, VeiculoEstoque veiculo, BigDecimal valorUnitario) {
        this.venda = venda;
        this.veiculo = veiculo;
        this.valorUnitario = valorUnitario;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public VeiculoEstoque getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(VeiculoEstoque veiculo) {
        this.veiculo = veiculo;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    @Override
    public String toString() {
        return "ItemVenda{" +
                "id=" + id +
                ", venda=" + (venda != null ? venda.getId() : null) +
                ", veiculo=" + (veiculo != null ? veiculo.getId() : null) +
                ", valorUnitario=" + valorUnitario +
                '}';
    }
}
