package com.grupo8.concessionaria.model.entities;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "veiculos_estoque")
public class VeiculoEstoque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_veiculo")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_modelo", nullable = false)
    private Modelo modelo;
    @Column(length = 17, nullable = false, unique = true)
    private String chassi;
    @Column(length = 7)
    private String placa;
    @Column(length = 30)
    private String cor;
    @Column(name = "ano_fabricacao")
    private Integer anoFabricacao;
    @Column(name = "ano_modelo")
    private Integer anoModelo;
    private Integer quilometragem = 0;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_veiculo", nullable = false)
    private StatusVeiculo statusVeiculo = StatusVeiculo.ESTOQUE;
    @Column(name = "preco_compra", precision = 10, scale = 2)
    private BigDecimal precoCompra;
    @Column(name = "preco_venda_sugerido", precision = 10, scale = 2)
    private BigDecimal precoVendaSugerido;

    public VeiculoEstoque() {}

    // GETTERS E SETTERS

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Modelo getModelo() { return modelo; }

    public void setModelo(Modelo modelo) { this.modelo = modelo; }

    public String getChassi() { return chassi; }

    public void setChassi(String chassi) { this.chassi = chassi; }

    public String getPlaca() { return placa; }

    public void setPlaca(String placa) { this.placa = placa; }

    public String getCor() { return cor; }

    public void setCor(String cor) { this.cor = cor; }

    public Integer getAnoFabricacao() { return anoFabricacao; }

    public void setAnoFabricacao(Integer anoFabricacao) { this.anoFabricacao = anoFabricacao; }

    public Integer getAnoModelo() { return anoModelo; }

    public void setAnoModelo(Integer anoModelo) { this.anoModelo = anoModelo; }

    public Integer getQuilometragem() { return quilometragem; }

    public void setQuilometragem(Integer quilometragem) { this.quilometragem = quilometragem; }

    public StatusVeiculo getStatusVeiculo() { return statusVeiculo; }

    public void setStatusVeiculo(StatusVeiculo statusVeiculo) {
        this.statusVeiculo = statusVeiculo;
    }

    public BigDecimal getPrecoCompra() { return precoCompra; }

    public void setPrecoCompra(BigDecimal precoCompra) { this.precoCompra = precoCompra; }

    public BigDecimal getPrecoVendaSugerido() { return precoVendaSugerido; }

    public void setPrecoVendaSugerido(BigDecimal precoVendaSugerido) {
        this.precoVendaSugerido = precoVendaSugerido;
    }
}
