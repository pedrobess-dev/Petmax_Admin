CREATE TABLE categoria (
    idcategoria SERIAL PRIMARY KEY,
    nomecategoria VARCHAR(50) NOT NULL,
    descricao VARCHAR(100) NOT NULL
); -- FINALIZADO

CREATE TABLE fornecedor (
    idfornecedor SERIAL PRIMARY KEY,
    nomefornecedor VARCHAR(70) NOT NULL,
    telefone VARCHAR(14) NOT NULL,
    cep VARCHAR(9) NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    rua VARCHAR(50) NOT NULL,
    numero INTEGER NOT NULL,
    cidade VARCHAR(30) NOT NULL,
    uf CHAR(2) NOT NULL,
    cnpj VARCHAR(18) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE
); -- FINALIZADO

CREATE TABLE produto (
    idproduto SERIAL PRIMARY KEY,
    nomeproduto VARCHAR(50) NOT NULL,
    preco NUMERIC NOT NULL,
    qtdEstoque INTEGER NOT NULL,
    dataFabricacao DATE NOT NULL,
    dataValidade DATE NOT NULL,
    idcategoria INTEGER NOT NULL,
    idfornecedor INTEGER NOT NULL,
    tempromocao VARCHAR(3) NOT NULL,
    CONSTRAINT fk_categoria FOREIGN KEY (idcategoria) REFERENCES categoria (idcategoria),
    CONSTRAINT fk_fornecedor FOREIGN KEY (idfornecedor) REFERENCES fornecedor (idfornecedor)
); -- FINALIZADO


CREATE TABLE reposicao (
    idreposicao SERIAL PRIMARY KEY,
    necessidade VARCHAR(10) NOT NULL,
    idproduto INTEGER NOT NULL,
    CONSTRAINT fk_produto FOREIGN KEY (idproduto) REFERENCES produto (idproduto)
); -- FINALIZADO

CREATE TABLE cliente (
    idcliente SERIAL PRIMARY KEY,
    nomecliente VARCHAR(50) NOT NULL,
    telefone VARCHAR(14) NOT NULL
); -- FINALIZADO

CREATE TABLE promissoria (
    idpromissoria SERIAL PRIMARY KEY,
    idcliente INTEGER NOT NULL,
    idproduto INTEGER NOT NULL,
    qtdVendida INTEGER NOT NULL,
    valor NUMERIC NOT NULL,
    status VARCHAR(10) NOT NULL,
    dataEmissao DATE NOT NULL,
    dataValidade DATE NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (idcliente) REFERENCES cliente (idcliente)
    CONSTRAINT fk_produto FOREIGN KEY (idproduto) REFERENCES produto (idproduto);
); -- FINALIZADO

CREATE TABLE venda (
    idvenda SERIAL PRIMARY KEY,
    idcliente INTEGER NOT NULL,
    idproduto INTEGER NOT NULL,
    qtdVendida INTEGER NOT NULL,
    valorVenda NUMERIC NOT NULL,
    dataVenda DATE NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (idcliente) REFERENCES cliente (idcliente),
    CONSTRAINT fk_produto FOREIGN KEY (idproduto) REFERENCES produto (idproduto)
); -- FINALIZADO

CREATE TABLE entrega (
    identrega SERIAL PRIMARY KEY,
    idvenda INTEGER NOT NULL,
    cep VARCHAR(9) NOT NULL,
    bairro VARCHAR(50) NOT NULL,
    rua VARCHAR(50) NOT NULL,
    numero INTEGER NOT NULL,
    CONSTRAINT fk_venda FOREIGN KEY (idvenda) REFERENCES venda (idvenda)
); --FINALIZADO

CREATE TABLE usuario (
    idusuario SERIAL PRIMARY KEY,
    nomeprestador VARCHAR(50) NOT NULL UNIQUE,
    nomeusuario VARCHAR(20) NOT NULL UNIQUE,
    senha VARCHAR NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    papel VARCHAR(13) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE
); -- FINALIZADO

CREATE TABLE promocao (
    idpromocao SERIAL PRIMARY KEY,
    idproduto INTEGER NOT NULL,
    precopromocao NUMERIC NOT NULL,
    CONSTRAINT fk_produto FOREIGN KEY (idproduto) REFERENCES produto (idproduto)
); -- FINALIZADO

--GERAR RELATÓRIOS
