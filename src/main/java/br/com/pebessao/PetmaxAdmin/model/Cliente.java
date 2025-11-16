package br.com.pebessao.PetmaxAdmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCliente;

    @NotBlank(message = "Nome do cliente é obrigatório")
    @Size(max = 50, message = "Nome do cliente não pode exceder 50 caracteres.")
    @Column(name = "nomecliente", nullable = false, length = 50)
    private String nomeCliente;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 14, message = "Telefone não pode exceder 14 caracteres.")
    @Column(name = "telefone", nullable = false, length = 14)
    private String telefone;

    public Cliente(Integer idCliente, String nomeCliente, String telefone) {
        this.idCliente = idCliente;
        this.nomeCliente = nomeCliente;
        this.telefone = telefone;
    }

    public Cliente() {
        this.idCliente = null;
        this.nomeCliente = "";
        this.telefone = "";
    }

    public Integer getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public String getNomeCliente() {
        return this.nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}