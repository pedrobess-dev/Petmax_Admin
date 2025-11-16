package br.com.pebessao.PetmaxAdmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "promocao")
public class Promocao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idpromocao")
    private Integer idPromocao;

    @NotNull(message = "Produto é obrigatório")
    @OneToOne
    @JoinColumn(name = "idproduto", nullable = false)
    private Produto produto;

    @NotNull(message = "Preço Promoção é obrigatório")
    @Column(name = "precopromocao", nullable = false)
    private double precoPromocao;

    public Promocao(Integer idPromocao, Produto produto, double precoPromocao) {
        this.idPromocao = idPromocao;
        this.produto = produto;
        this.precoPromocao = precoPromocao;
    }

    public Promocao() {
        this.idPromocao = null;
        this.produto = null;
        this.precoPromocao = 0.0;
    }

    public Integer getIdPromocao() {
        return this.idPromocao;
    }

    public void setIdPromocao(Integer idPromocao) {
        this.idPromocao = idPromocao;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getPrecoPromocao() {
        return this.precoPromocao;
    }

    public void setPrecoPromocao(double precoPromocao) {
        this.precoPromocao = precoPromocao;
    }
}
