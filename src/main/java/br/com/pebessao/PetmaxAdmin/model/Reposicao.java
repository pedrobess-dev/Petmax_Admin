package br.com.pebessao.PetmaxAdmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "reposicao")
public class Reposicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReposicao;

    @NotBlank(message = "Necessidade é obrigatório")
    @Size(max = 10, message = "Necessidade não pode exceder 10 caracteres.")
    @Column(name = "necessidade", nullable = false, length = 10)
    private String necessidade;

    @NotNull(message = "Produto é obrigatório")
    @OneToOne
    @JoinColumn(name = "idproduto", nullable = false)
    private Produto produto;

    public Reposicao(Integer idReposicao, String necessidade, Produto produto) {
        this.idReposicao = idReposicao;
        this.necessidade = necessidade;
        this.produto = produto;
    }

    public Reposicao() {
        this.idReposicao = 0;
        this.necessidade = "";
        this.produto = null;
    }

    public Integer getIdReposicao() {
        return this.idReposicao;
    }

    public void setIdReposicao(Integer idReposicao) {
        this.idReposicao = idReposicao;
    }

    public String getNecessidade() {
        return this.necessidade;
    }

    public void setNecessidade(String necessidade) {
        this.necessidade = necessidade;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}