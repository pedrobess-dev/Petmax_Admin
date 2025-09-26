package br.com.pebessao.PetmaxAdmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "fornecedor")
public class Fornecedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idfornecedor")
    private Integer idFornecedor;

    @NotBlank(message = "Nome do fornecedor é obrigatório")
    @Size(max = 50, message = "Nome do fornecedor não pode exceder 50 caracteres.")
    @Column(name = "nomefornecedor", nullable = false, length = 50)
    private String nomeFornecedor;

    @NotBlank(message = "Telefone é obrigatório")
    @Size(max = 14, message = "Telefone não pode exceder 14 caracteres.")
    @Column(name = "telefone", nullable = false, length = 14)
    private String telefone;

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

    @NotBlank(message = "Cidade é obrigatório")
    @Size(max = 30, message = "Cidade não pode exceder 30 caracteres.")
    @Column(name = "cidade", nullable = false, length = 30)
    private String cidade;

    @NotBlank(message = "UF é obrigatório")
    @Size(max = 2, message = "UF não pode exceder 2 caracteres.")
    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @NotBlank(message = "CNPJ é obrigatório")
    @Size(max = 18, message = "CNPJ não pode exceder 18 caracteres.")
    @Column(name = "cnpj", nullable = false, length = 18)
    private String cnpj;

    @NotBlank(message = "Email é obrigatório")
    @Size(max = 50, message = "Email não pode exceder 50 caracteres.")
    @Email(message = "Formato de email inválido.")
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    public Fornecedor() {
        this.idFornecedor = 0;
        this.nomeFornecedor = "";
        this.telefone = "";
        this.cep = "";
        this.bairro = "";
        this.rua = "";
        this.numero = 0;
        this.cidade = "";
        this.uf = "";
        this.cnpj = "";
        this.email = "";
    }

    public Fornecedor(Integer idFornecedor, String nomeFornecedor, String telefone, String cep,
                      String bairro, String rua, Integer numero, String cidade, String uf, String cnpj,
                      String email) {
        this.idFornecedor = idFornecedor;
        this.nomeFornecedor = nomeFornecedor;
        this.telefone = telefone;
        this.cep = cep;
        this.bairro = bairro;
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.uf = uf;
        this.cnpj = cnpj;
        this.email = email;
    }

    public Integer getIdFornecedor() {
        return this.idFornecedor;
    }

    public void setIdFornecedor(Integer idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public String getNomeFornecedor() {
        return this.nomeFornecedor;
    }

    public void setNomeFornecedor(String nomeFornecedor) {
        this.nomeFornecedor = nomeFornecedor;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
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

    public String getCidade() {
        return this.cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return this.uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getCnpj() {
        return this.cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}