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
@Table(name = "promissoria")
public class Promissoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPromissoria;

    @NotNull(message = "Cliente é obrigatório")
    @ManyToOne
    @JoinColumn(name = "idcliente", nullable = false)
    private Cliente cliente;

    @NotNull(message = "Produto é obrigatório")
    @ManyToOne
    @JoinColumn(name = "idproduto", nullable = false)
    private Produto produto;

    @NotNull(message = "Quantidade é obrigatório")
    @Column(name = "qtdVendida", nullable = false)
    private Integer qtdVendida;

    @NotNull(message = "Valor é obrigatório")
    @Column(name = "valor", nullable = false)
    private double valor;

    @NotBlank(message = "Status é obrigatório")
    @Size(max = 10, message = "Status não pode exceder 10 caracteres.")
    @Column(name = "status", nullable = false, length = 10)
    private String status;

    @NotNull(message = "Data de Emissão é obrigatório")
    @Column(name = "dataEmissao", nullable = false)
    private LocalDate dataEmissao;

    @NotNull(message = "Data de Validade é obrigatório")
    @Column(name = "dataValidade", nullable = false)
    private LocalDate dataValidade;

    public Promissoria(Integer idPromissoria, Cliente cliente, Produto produto, Integer qtdVendida,
                       double valor, String status, LocalDate dataEmissao, LocalDate dataValidade) {
        this.idPromissoria = idPromissoria;
        this.cliente = cliente;
        this.produto = produto;
        this.qtdVendida = qtdVendida;
        this.valor = valor;
        this.status = status;
        this.dataEmissao = dataEmissao;
        this.dataValidade = dataValidade;
    }

    public Promissoria() {
        this.idPromissoria = null;
        this.cliente = null;
        this.produto = null;
        this.qtdVendida = 0;
        this.valor = 0.0;
        this.status = "";
        this.dataEmissao = LocalDate.now();
        this.dataValidade = LocalDate.now();
    }

    public Integer getIdPromissoria() {
        return this.idPromissoria;
    }

    public void setIdPromissoria(Integer idPromissoria) {
        this.idPromissoria = idPromissoria;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Integer getQtdVendida() {
        return qtdVendida;
    }

    public void setQtdVendida(Integer qtdVendida) {
        this.qtdVendida = qtdVendida;
    }

    public double getValor() {
        return this.valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataEmissao() {
        return this.dataEmissao;
    }

    public void setDataEmissao(LocalDate dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public LocalDate getDataValidade() {
        return this.dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getDataEmiFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataEmissao != null ? this.dataEmissao.format(formatter) : "";
    }

    public String getDataValiFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataValidade != null ? this.dataValidade.format(formatter) : "";
    }
}