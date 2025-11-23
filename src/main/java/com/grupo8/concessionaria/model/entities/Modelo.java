package com.grupo8.concessionaria.model.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "modelos")
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private Long id;
    @Column(length = 80, nullable = false)
    private String nome;
    @Column(length = 50, nullable = false)
    private String marca;
    @Column(name = "ano_lancamento")
    private Integer anoLancamento;
    @Column(name = "tipo_combustivel", length = 20)
    private String tipoCombustivel;
    @Column(length = 30)
    private String categoria;
    public Modelo() {
    }

    public Modelo(String nome, String marca, Integer anoLancamento,
                  String tipoCombustivel, String categoria) {
        this.nome = nome;
        this.marca = marca;
        this.anoLancamento = anoLancamento;
        this.tipoCombustivel = tipoCombustivel;
        this.categoria = categoria;
    }

    // getters e setters

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getNome() { return nome; }

    public void setNome(String nome) { this.nome = nome; }

    public String getMarca() { return marca; }

    public void setMarca(String marca) { this.marca = marca; }

    public Integer getAnoLancamento() { return anoLancamento; }

    public void setAnoLancamento(Integer anoLancamento) { this.anoLancamento = anoLancamento; }

    public String getTipoCombustivel() { return tipoCombustivel; }

    public void setTipoCombustivel(String tipoCombustivel) { this.tipoCombustivel = tipoCombustivel; }

    public String getCategoria() { return categoria; }

    public void setCategoria(String categoria) { this.categoria = categoria; }
}
