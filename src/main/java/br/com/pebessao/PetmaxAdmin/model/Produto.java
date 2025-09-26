package br.com.pebessao.PetmaxAdmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idproduto")
    private Integer idProduto;

    @NotBlank(message = "Nome do produto é obrigatório")
    @Size(max = 50, message = "Nome do produto não pode exceder 50 caracteres.")
    @Column(name = "nomeproduto", nullable = false, length = 50)
    private String nomeProduto;

    @NotNull(message = "Preço é obrigatório")
    @Column(name = "preco", nullable = false)
    private double preco;

    @NotNull(message = "Categoria é obrigatório")
    @ManyToOne
    @JoinColumn(name = "idcategoria", nullable = false)
    private Categoria categoria;

    @NotNull(message = "Fornecedor é obrigatório")
    @ManyToOne
    @JoinColumn(name = "idfornecedor", nullable = false)
    private Fornecedor fornecedor;

    @NotNull(message = "Quantidade é obrigatório")
    @Column(name = "qtdEstoque", nullable = false)
    private Integer qtdEstoque;

    @NotNull(message = "Data de Fabricação é obrigatório")
    @Column(name = "dataFabricacao", nullable = false)
    private LocalDate dataFabricacao;

    @NotNull(message = "Data de Validade é obrigatório")
    @Column(name = "dataValidade", nullable = false)
    private LocalDate dataValidade;

    @NotNull(message = "Em Promoção é obrigatório")
    @Column(name = "tempromocao", nullable = false)
    private boolean temPromocao;

    public Produto(Integer idProduto, String nomeProduto, double preco, Categoria categoria,
                   Fornecedor fornecedor, Integer qtdEstoque, LocalDate dataFabricacao,
                   LocalDate dataValidade, boolean temPromocao) {
        this.idProduto = idProduto;
        this.nomeProduto = nomeProduto;
        this.preco = preco;
        this.categoria = categoria;
        this.fornecedor = fornecedor;
        this.qtdEstoque = qtdEstoque;
        this.dataFabricacao = dataFabricacao;
        this.dataValidade = dataValidade;
        this.temPromocao = temPromocao;
    }

    public Produto() {
        this.idProduto = 0;
        this.nomeProduto = "";
        this.preco = 0.0;
        this.categoria = null;
        this.fornecedor = null;
        this.qtdEstoque = 0;
        this.dataFabricacao = LocalDate.now();
        this.dataValidade = LocalDate.now();
        this.temPromocao = false;
    }

    public Integer getIdProduto() {
        return this.idProduto;
    }

    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }

    public String getNomeProduto() {
        return this.nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public double getPreco() {
        return this.preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public Categoria getCategoria() {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Fornecedor getFornecedor() {
        return this.fornecedor;
    }

    public void setFornecedor(Fornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Integer getQtdEstoque() {
        return this.qtdEstoque;
    }

    public void setQtdEstoque(Integer qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public LocalDate getDataFabricacao() {
        return this.dataFabricacao;
    }

    public void setDataFabricacao(LocalDate dataFabricacao) {
        this.dataFabricacao = dataFabricacao;
    }

    public LocalDate getDataValidade() {
        return this.dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public boolean isTemPromocao() {
        return this.temPromocao;
    }

    public void setTemPromocao(boolean temPromocao) {
        this.temPromocao = temPromocao;
    }

    public String getDataFabriFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataFabricacao != null ? this.dataFabricacao.format(formatter) : "";
    }

    public String getDataValiFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataValidade != null ? this.dataValidade.format(formatter) : "";
    }
}