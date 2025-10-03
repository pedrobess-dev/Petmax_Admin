package br.com.pebessao.PetmaxAdmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "venda")
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idVenda;

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
    @Column(name = "valorVenda", nullable = false)
    private double valorVenda;

    @NotNull(message = "Data da Venda é obrigatório")
    @Column(name = "dataVenda", nullable = false)
    private LocalDate dataVenda;

    public Venda(Integer idVenda, Cliente cliente, Produto produto, Integer qtdVendida,
                 double valorVenda, LocalDate dataVenda) {
        this.idVenda = idVenda;
        this.cliente = cliente;
        this.produto = produto;
        this.qtdVendida = qtdVendida;
        this.valorVenda = valorVenda;
        this.dataVenda = dataVenda;
    }

    public Venda() {
        this.idVenda = 0;
        this.cliente = null;
        this.produto = null;
        this.qtdVendida = 0;
        this.valorVenda = 0.0;
        this.dataVenda = LocalDate.now();
    }

    public Integer getIdVenda() {
        return this.idVenda;
    }

    public void setIdVenda(Integer idVenda) {
        this.idVenda = idVenda;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Produto getProduto() {
        return this.produto;
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

    public double getValorVenda() {
        return this.valorVenda;
    }

    public void setValorVenda(double valorVenda) {
        this.valorVenda = valorVenda;
    }

    public LocalDate getDataVenda() {
        return this.dataVenda;
    }

    public void setDataVenda(LocalDate dataVenda) {
        this.dataVenda = dataVenda;
    }

    public String getDataVenFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.dataVenda != null ? this.dataVenda.format(formatter) : "";
    }
}