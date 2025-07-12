CREATE TABLE IF NOT EXISTS usuarios
(
    id                     INT PRIMARY KEY AUTO_INCREMENT,
    nome                   VARCHAR(100)  NOT NULL,
    email                  VARCHAR(100)  NOT NULL UNIQUE,
    porcentagem_corretagem DECIMAL(5, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS ativos_renda_variavel
(
    id     INT PRIMARY KEY AUTO_INCREMENT,
    codigo VARCHAR(6)   NOT NULL UNIQUE,
    nome   VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS operacoes
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id     INT                     NOT NULL,
    ativo_id       INT                     NOT NULL,
    quantidade     INT UNSIGNED            NOT NULL,
    preco_unitario DECIMAL(10, 4) UNSIGNED NOT NULL,
    tipo_operacao  TINYINT UNSIGNED        NOT NULL,
    corretagem     DECIMAL(10, 4) UNSIGNED NOT NULL,
    data_hora      DATETIME(3)             NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    CONSTRAINT fk_operacoes_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_operacoes_ativo FOREIGN KEY (ativo_id) REFERENCES ativos_renda_variavel (id)
);

CREATE TABLE IF NOT EXISTS cotacoes
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    ativo_id       INT            NOT NULL,
    preco_unitario DECIMAL(10, 4) NOT NULL,
    data_hora      DATETIME(3)    NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
    CONSTRAINT fk_cotacoes_ativo FOREIGN KEY (ativo_id) REFERENCES ativos_renda_variavel (id)
);

CREATE TABLE IF NOT EXISTS posicao
(
    id                BIGINT PRIMARY KEY AUTO_INCREMENT,
    usuario_id        INT            NOT NULL,
    ativo_id          INT            NOT NULL,
    quantidade        INT UNSIGNED   NOT NULL,
    preco_medio       DECIMAL(10, 4) NOT NULL,
    profit_loss_total DECIMAL(10, 4) NOT NULL,
    CONSTRAINT fk_posicao_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
    CONSTRAINT fk_posicao_ativo FOREIGN KEY (ativo_id) REFERENCES ativos_renda_variavel (id)
);