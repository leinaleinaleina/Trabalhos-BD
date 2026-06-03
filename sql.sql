USE Banco_agencia;

CREATE TABLE UF (
    idUF INT NOT NULL AUTO_INCREMENT,
    UF VARCHAR(45) NULL,
    PRIMARY KEY (idUF)
);

CREATE TABLE Bairro (
    idBairro INT NOT NULL AUTO_INCREMENT,
    Bairro VARCHAR(45) NULL,
    PRIMARY KEY (idBairro)
);

CREATE TABLE Tipo_logradouro (
    idTipo_logradouro INT NOT NULL AUTO_INCREMENT,
    Tipo_logradouro VARCHAR(45) NULL,
    PRIMARY KEY (idTipo_logradouro)
);

CREATE TABLE DDDI (
    idDDDI INT NOT NULL AUTO_INCREMENT,
    DDDI VARCHAR(45) NULL,
    PRIMARY KEY (idDDDI)
);

CREATE TABLE Banco (
    Codbanco INT NOT NULL,
    Nomebanco VARCHAR(45) NULL,
    CNPJ VARCHAR(45) NULL,
    PRIMARY KEY (Codbanco)
);

CREATE TABLE Tipoinvestimento (
    idTipoinvestimento INT NOT NULL AUTO_INCREMENT,
    Tipoinvestimento VARCHAR(45) NULL,
    PRIMARY KEY (idTipoinvestimento)
);

CREATE TABLE Tipotransacao (
    idTipotransacao INT NOT NULL AUTO_INCREMENT,
    Tipotransacao VARCHAR(45) NULL,
    PRIMARY KEY (idTipotransacao)
);

-- =========================================================
-- TABELAS DE NÍVEL 1 (Dependem de 1 tabela)
-- =========================================================

CREATE TABLE Cidade (
    idCidade INT NOT NULL AUTO_INCREMENT,
    Cidade VARCHAR(45) NULL,
    UF_idUF INT NOT NULL,
    PRIMARY KEY (idCidade),
    FOREIGN KEY (UF_idUF) REFERENCES UF (idUF)
);

CREATE TABLE Logradouro (
    idLogradouro INT NOT NULL AUTO_INCREMENT,
    Logradouro VARCHAR(45) NULL,
    Tipo_logradouro_idTipo_logradouro INT NOT NULL,
    PRIMARY KEY (idLogradouro),
    FOREIGN KEY (Tipo_logradouro_idTipo_logradouro) REFERENCES Tipo_logradouro (idTipo_logradouro)
);

CREATE TABLE DDD (
    idDDD INT NOT NULL AUTO_INCREMENT,
    DDD VARCHAR(45) NULL,
    DDDI_idDDDI INT NOT NULL,
    PRIMARY KEY (idDDD),
    FOREIGN KEY (DDDI_idDDDI) REFERENCES DDDI (idDDDI)
);

-- =========================================================
-- TABELAS DE NÍVEL 2 (Endereço e dependentes de Endereço)
-- =========================================================

CREATE TABLE Endereco (
    idEndereco INT NOT NULL AUTO_INCREMENT,
    CEP VARCHAR(45) NULL,
    Bairro_idBairro INT NOT NULL,
    Logradouro_idLogradouro INT NOT NULL,
    Cidade_idCidade INT NOT NULL,
    PRIMARY KEY (idEndereco),
    FOREIGN KEY (Bairro_idBairro) REFERENCES Bairro (idBairro),
    FOREIGN KEY (Logradouro_idLogradouro) REFERENCES Logradouro (idLogradouro),
    FOREIGN KEY (Cidade_idCidade) REFERENCES Cidade (idCidade)
);

CREATE TABLE Cliente (
    idCliente INT NOT NULL AUTO_INCREMENT,
    Nomecliente VARCHAR(45) NULL,
    CPF VARCHAR(45) NULL,
    Complemento VARCHAR(45) NULL,
    Numero VARCHAR(45) NULL,
    Endereco_idEndereco INT NOT NULL,
    PRIMARY KEY (idCliente),
    FOREIGN KEY (Endereco_idEndereco) REFERENCES Endereco (idEndereco)
);

-- Nota: Relacionamento Identificador com Banco (Chave Primária Composta)
CREATE TABLE Agencia (
    Numeroagencia INT NOT NULL,
    Tipoagencia INT NULL,
    Banco_Codbanco INT NOT NULL,
    Endereco_idEndereco INT NOT NULL,
    PRIMARY KEY (Numeroagencia, Banco_Codbanco),
    FOREIGN KEY (Banco_Codbanco) REFERENCES Banco (Codbanco),
    FOREIGN KEY (Endereco_idEndereco) REFERENCES Endereco (idEndereco)
);

-- =========================================================
-- TABELAS DE NÍVEL 3 (Contactos e Contas)
-- =========================================================

CREATE TABLE Emailcliente (
    idEmailcliente INT NOT NULL AUTO_INCREMENT,
    Emailcliente VARCHAR(45) NULL,
    Cliente_idCliente INT NOT NULL,
    PRIMARY KEY (idEmailcliente),
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente (idCliente)
);

CREATE TABLE Fonecliente (
    idFonecliente INT NOT NULL AUTO_INCREMENT,
    Fonecliente VARCHAR(45) NULL,
    DDD_idDDD INT NOT NULL,
    Cliente_idCliente INT NOT NULL,
    PRIMARY KEY (idFonecliente),
    FOREIGN KEY (DDD_idDDD) REFERENCES DDD (idDDD),
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente (idCliente)
);

CREATE TABLE Emailagencia (
    idEmailagencia INT NOT NULL AUTO_INCREMENT,
    Emailagencia VARCHAR(45) NULL,
    Agencia_Numeroagencia INT NOT NULL,
    Agencia_Banco_Codbanco INT NOT NULL,
    PRIMARY KEY (idEmailagencia),
    FOREIGN KEY (Agencia_Numeroagencia, Agencia_Banco_Codbanco) REFERENCES Agencia (Numeroagencia, Banco_Codbanco)
);

CREATE TABLE Foneagencia (
    idFoneagencia INT NOT NULL AUTO_INCREMENT,
    -- Nota: No seu MER aparece "Fonecliente" dentro desta tabela devido a um provável erro de copy-paste. 
    -- Corrigi aqui para "Foneagencia" para fazer sentido.
    Foneagencia VARCHAR(45) NULL, 
    DDD_idDDD INT NOT NULL,
    Agencia_Numeroagencia INT NOT NULL,
    Agencia_Banco_Codbanco INT NOT NULL,
    PRIMARY KEY (idFoneagencia),
    FOREIGN KEY (DDD_idDDD) REFERENCES DDD (idDDD),
    FOREIGN KEY (Agencia_Numeroagencia, Agencia_Banco_Codbanco) REFERENCES Agencia (Numeroagencia, Banco_Codbanco)
);

-- Nota: Relacionamento Identificador com Agencia (Chave Primária Composta de 3 campos)
CREATE TABLE Contabancaria (
    idContabancaria INT NOT NULL,
    Saldo VARCHAR(45) NULL,
    Agencia_Numeroagencia INT NOT NULL,
    Agencia_Banco_Codbanco INT NOT NULL,
    Cliente_idCliente INT NOT NULL,
    PRIMARY KEY (idContabancaria, Agencia_Numeroagencia, Agencia_Banco_Codbanco),
    FOREIGN KEY (Agencia_Numeroagencia, Agencia_Banco_Codbanco) REFERENCES Agencia (Numeroagencia, Banco_Codbanco),
    FOREIGN KEY (Cliente_idCliente) REFERENCES Cliente (idCliente)
);

-- =========================================================
-- TABELAS DE NÍVEL 4 (Transações e Investimentos)
-- =========================================================

CREATE TABLE Investimento (
    idInvestimento INT NOT NULL AUTO_INCREMENT,
    Datainvestimento VARCHAR(45) NULL,
    Valorinvestimento VARCHAR(45) NULL,
    Tipoinvestimento_idTipoinvestimento INT NOT NULL,
    Contabancaria_idContabancaria INT NOT NULL,
    Contabancaria_Agencia_Numeroagencia INT NOT NULL,
    Contabancaria_Agencia_Banco_Codbanco INT NOT NULL,
    PRIMARY KEY (idInvestimento),
    FOREIGN KEY (Tipoinvestimento_idTipoinvestimento) REFERENCES Tipoinvestimento (idTipoinvestimento),
    FOREIGN KEY (Contabancaria_idContabancaria, Contabancaria_Agencia_Numeroagencia, Contabancaria_Agencia_Banco_Codbanco) 
        REFERENCES Contabancaria (idContabancaria, Agencia_Numeroagencia, Agencia_Banco_Codbanco)
);

CREATE TABLE Transacao (
    idTransacao INT NOT NULL AUTO_INCREMENT,
    Valortransacao VARCHAR(45) NULL,
    Data_transacao VARCHAR(45) NULL,
    Tipotransacao_idTipotransacao INT NOT NULL,
    Contabancaria_idContabancaria INT NOT NULL,
    Contabancaria_Agencia_Numeroagencia INT NOT NULL,
    Contabancaria_Agencia_Banco_Codbanco INT NOT NULL,
    PRIMARY KEY (idTransacao),
    FOREIGN KEY (Tipotransacao_idTipotransacao) REFERENCES Tipotransacao (idTipotransacao),
    FOREIGN KEY (Contabancaria_idContabancaria, Contabancaria_Agencia_Numeroagencia, Contabancaria_Agencia_Banco_Codbanco) 
        REFERENCES Contabancaria (idContabancaria, Agencia_Numeroagencia, Agencia_Banco_Codbanco)
);