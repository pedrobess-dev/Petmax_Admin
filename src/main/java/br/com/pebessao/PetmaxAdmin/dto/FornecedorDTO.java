package br.com.pebessao.PetmaxAdmin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FornecedorDTO {

    @JsonProperty("razao_social")
    private String razaoSocial;

    @JsonProperty("estabelecimento")
    private Estabelecimento estabelecimento;

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public static class Estabelecimento {

        @JsonProperty("logradouro")
        private String logradouro;

        @JsonProperty("numero")
        private String numero;

        @JsonProperty("bairro")
        private String bairro;

        @JsonProperty("cidade")
        private Cidade cidade;

        @JsonProperty("estado")
        private Estado estado;

        @JsonProperty("cep")
        private String cep;

        @JsonProperty("ddd1")
        private String ddd;

        @JsonProperty("telefone1")
        private String telefone;

        @JsonProperty("email")
        private String email;

        @JsonProperty("cnpj")
        private String cnpj;

        public String getLogradouro() {
            return logradouro;
        }

        public void setLogradouro(String logradouro) {
            this.logradouro = logradouro;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getBairro() {
            return bairro;
        }

        public void setBairro(String bairro) {
            this.bairro = bairro;
        }

        public Cidade getCidade() {
            return cidade;
        }

        public void setCidade(Cidade cidade) {
            this.cidade = cidade;
        }

        public Estado getEstado() {
            return estado;
        }

        public void setEstado(Estado estado) {
            this.estado = estado;
        }

        public String getCep() {
            return cep;
        }

        public void setCep(String cep) {
            this.cep = cep;
        }

        public String getDdd() {
            return ddd;
        }

        public void setDdd(String ddd) {
            this.ddd = ddd;
        }

        public String getTelefone() {
            return telefone;
        }

        public void setTelefone(String telefone) {
            this.telefone = telefone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCnpj() {
            return cnpj;
        }

        public void setCnpj(String cnpj) {
            this.cnpj = cnpj;
        }
    }

    public static class Cidade {

        @JsonProperty("nome")
        private String nome;

        public String getNome() {
            return nome;
        }

        public void setNome(String nome) {
            this.nome = nome;
        }
    }

    public static class Estado {

        @JsonProperty("sigla")
        private String sigla;

        public String getSigla() {
            return sigla;
        }

        public void setSigla(String sigla) {
            this.sigla = sigla;
        }
    }
}
