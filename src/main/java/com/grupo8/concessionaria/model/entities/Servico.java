package com.grupo8.concessionaria.model.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "servicos")
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_servico")
    private Long id;

    @Column(length = 100, nullable = false)
    private String descricao;

    @Column(name = "valor_mao_obra_base", nullable = false, precision = 10, scale = 2)
    private BigDecimal valorMaoObraBase;

    public Servico() {
    }

    public Servico(String descricao, BigDecimal valorMaoObraBase) {
        this.descricao = descricao;
        this.valorMaoObraBase = valorMaoObraBase;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValorMaoObraBase() {
        return valorMaoObraBase;
    }

    public void setValorMaoObraBase(BigDecimal valorMaoObraBase) {
        this.valorMaoObraBase = valorMaoObraBase;
    }

    @Override
    public String toString() {
        return "Servico{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                ", valorMaoObraBase=" + valorMaoObraBase +
                '}';
    }
}