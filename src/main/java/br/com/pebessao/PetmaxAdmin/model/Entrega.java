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

@Entity
@Table(name = "entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntrega;

    @NotNull(message = "Venda é obrigatório")
    @ManyToOne
    @JoinColumn(name = "idvenda", nullable = false)
    private Venda venda;

    @NotBlank(message = "CEP é obrigatório")
    @Size(max = 9, message = "CEP não pode exceder 9 caracteres.")
    @Column(name = "cep", nullable = false, length = 9)
    private String cep;

    @NotBlank(message = "Bairro é obrigatório")
    @Size(max = 50, message = "Bairro não pode exceder 50 caracteres.")
    @Column(name = "bairro", nullable = false, length = 50)
    private String bairro;

    @NotBlank(message = "Rua é obrigatório")
    @Size(max = 50, message = "Rua não pode exceder 50 caracteres.")
    @Column(name = "rua", nullable = false, length = 50)
    private String rua;

    @NotNull(message = "N° é obrigatório")
    @Column(name = "numero", nullable = false)
    private Integer numero;

    public Entrega(Integer idEntrega, Venda venda, String cep, String bairro, String rua, Integer numero) {
        this.idEntrega = idEntrega;
        this.venda = venda;
        this.cep = cep;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = 0;
    }

    public Entrega() {
        this.idEntrega = null;
        this.venda = null;
        this.cep = "";
        this.bairro = "";
        this.rua = "";
        this.numero = 0;
    }

    public Integer getIdEntrega() {
        return this.idEntrega;
    }

    public void setIdEntrega(Integer idEntrega) {
        this.idEntrega = idEntrega;
    }

    public Venda getVenda() {
        return this.venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    public String getCep() {
        return this.cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return this.bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return this.rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public Integer getNumero() {
        return this.numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }
}