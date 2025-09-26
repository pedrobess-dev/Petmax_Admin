package br.com.pebessao.PetmaxAdmin.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @NotBlank(message = "Nome do prestador é obrigatório")
    @Size(max = 50, message = "Nome do prestador não pode exceder 50 caracteres.")
    @Column(name = "nomeprestador", nullable = false, length = 50)
    private String nomePrestador;

    @NotBlank(message = "Nome do usuário é obrigatório")
    @Size(max = 20, message = "Nome do usuário não pode exceder 20 caracteres.")
    @Column(name = "nomeusuario", nullable = false, length = 20)
    private String nomeUsuario;

    @NotBlank(message = "Senha é obrigatório")
    @Size(max = 8, message = "Senha não pode exceder 8 caracteres.")
    @Column(name = "senha", nullable = false, length = 8)
    private String senha;

    @NotBlank(message = "Email é obrigatório")
    @Size(max = 50, message = "Email não pode exceder 50 caracteres.")
    @Email(message = "Formato de email inválido")
    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @NotBlank(message = "Papel é obrigatório")
    @Size(max = 13, message = "Papel não pode exceder 13 caracteres.")
    @Column(name = "papel", nullable = false, length = 13)
    private String papel;

    @NotBlank(message = "CPF é obrigatório")
    @Size(max = 14, message = "CPF não pode exceder 14 caracteres.")
    @Column(name = "cpf", nullable = false)
    private String cpf;

    public Usuario(Integer idUsuario, String nomePrestador, String nomeUsuario, String senha,
                   String email, String papel, String cpf) {
        this.idUsuario = idUsuario;
        this.nomePrestador = nomePrestador;
        this.nomeUsuario = nomeUsuario;
        this.senha = senha;
        this.email = email;
        this.papel = papel;
        this.cpf = cpf;
    }

    public Usuario() {
        this.idUsuario = 0;
        this.nomePrestador = "";
        this.nomeUsuario = "";
        this.senha = "";
        this.email = "";
        this.papel = "";
        this.cpf = "";
    }

    public Integer getIdUsuario() {
        return this.idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomePrestador() {
        return this.nomePrestador;
    }

    public void setNomePrestador(String nomePrestador) {
        this.nomePrestador = nomePrestador;
    }

    public String getNomeUsuario() {
        return this.nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPapel() {
        return this.papel;
    }

    public void setPapel(String papel) {
        this.papel = papel;
    }

    public String getCpf() {
        return this.cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}