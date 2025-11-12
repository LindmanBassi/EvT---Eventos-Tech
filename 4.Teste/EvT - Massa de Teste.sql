-- ============================================================
-- BANCO DE DADOS: EvT
-- ============================================================
CREATE DATABASE IF NOT EXISTS EvT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE EvT;

-- ============================================================
-- TABELA: usuarios
-- ============================================================
CREATE TABLE usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cpf CHAR(11) NOT NULL UNIQUE,
    cargo ENUM('GERENTE','ANALISTA','ESTAGIARIO','COORDENADOR','APRENDIZ','VISITANTE') NOT NULL DEFAULT 'VISITANTE',
    departamento ENUM('FINANCEIRO','TI','RH','JURIDICO','MARKETING') NULL
) ENGINE=InnoDB;

-- ============================================================
-- TABELA: locais
-- ============================================================
CREATE TABLE locais (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE,
    capacidade INT NOT NULL,
    rua VARCHAR(255),
    bairro VARCHAR(255),
    cidade VARCHAR(255),
    estado VARCHAR(255),
    numero INT NOT NULL,
    cep VARCHAR(20) NOT NULL
) ENGINE=InnoDB;

-- ============================================================
-- TABELA: eventos
-- ============================================================
CREATE TABLE eventos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    tipo_evento ENUM('REMOTO','PRESENCIAL','HIBRIDO') NOT NULL,
    estado_evento ENUM('ABERTO','FECHADO') NOT NULL DEFAULT 'ABERTO',
    data DATETIME NOT NULL,
    descricao TEXT,
    titulo VARCHAR(255) NOT NULL,
    vagas INT NOT NULL,
    local_id BIGINT NULL,
    palestrante_id BIGINT NOT NULL,
    CONSTRAINT fk_evento_local FOREIGN KEY (local_id) REFERENCES locais(id)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT fk_evento_palestrante FOREIGN KEY (palestrante_id) REFERENCES usuarios(id)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

-- ============================================================
-- TABELA: participacoes_evento
-- ============================================================
CREATE TABLE participacoes_evento (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    evento_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    CONSTRAINT fk_participacao_evento FOREIGN KEY (evento_id) REFERENCES eventos(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_participacao_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT unq_participacao UNIQUE (evento_id, usuario_id)
) ENGINE=InnoDB;

-- ============================================================
-- USUÁRIOS (válidos e inválidos)
-- ============================================================

INSERT INTO usuarios (email, senha, nome, cpf, cargo, departamento) VALUES
-- Funcionários
('gerente@evt.com', '$2a$10$sHp/9A2BlEqww3L/lwoQdOJitd7OJrpFL7tM57Fiql/eOgs93AQDq', 'Carlos Gerente', '97037384003', 'GERENTE', 'TI'),
('ana.analista@evt.com', '$2a$10$cskoZ3YryuTDmU28FkNbHebugFZ2nt3IkOXkp.dkBvo659u3Ah0nS', 'Ana Analista', '95234881040', 'ANALISTA', 'RH'),
('joao.coord@evt.com', '$2a$10$zr1fIQj.eAGGLHoDsN3E5eXvazjtfR14lRuq9Kb1dUsKxyNw/C/se', 'João Coordenador', '31718115024', 'COORDENADOR', 'MARKETING'),
-- ('paula.aprendiz@evt.com', '12', 'Paula Aprendiz', '39275075042', 'APRENDIZ', 'TI'),
('marcio.estagiario@evt.com', '$2a$10$pq.Jtdy1xs344s/.lKH3/OeCVcrB0zXslgFHMzz4WmI4CkeR45zWC', 'Márcio Estagiário', '54919816057', 'ESTAGIARIO', 'FINANCEIRO'),
('juliana@evt.com', '$2a$10$TJYG9ub0.3N6VyYqgiR8VOXWCNyEL/rSuICh64ir/gbSpF0t24BPm', 'Juliana Líder', '81564331067', 'ANALISTA', 'RH'),
-- ('julianaG@evt.com', '123456', 'Juliana Líder', '81564331067', 'GERENTE', 'RH'),
('joao.coord.com', '$2a$10$dIVKU7/4SNXQi98OaRUX.O4zQE4BtXRXUc2vHNaVjiF/nbALlG7zK', 'João', '25095754061', 'COORDENADOR', 'TI'),
-- ('rogerio@evt.com', '123456', '', '65637989001', 'APRENDIZ', 'RH'),
-- ('', '123456', 'Marcia', '93860197010', 'ANALISTA', 'TI'),
-- ('carlosCoord@evt.com', '123456', 'Carlos', '', 'COORDENADOR', 'TI'),
-- ('ana@evt.com', '123456', 'Ana Maria', '44424381021', '', 'RH'),
-- ('rubens@evt.com', '123456', 'Rubens', '81253838089', 'ANALISTA', ''),
-- ('sabrina@evt.com', '', 'Sabrina', '95234881040', 'COORDENADOR', 'RH'),

-- Visitantes válidos (sem departamento)
('visit1@evt.com', '$2a$10$y99kzXJ9wV.ta5Fo5PoJD.Y3w8srXuQftxSSk/UOlADmU6H0unEga', 'Visitante 1', '98686006000', 'VISITANTE', NULL),
('visit2@evt.com', '$2a$10$IJNyTx4RdVMqaFIgbLTNwOfA9b4nMnUT1jkGBheBfVcO5mWrWkueu', 'Visitante 2', '07478267068', 'VISITANTE', NULL),
('visit3@evt.com', '$2a$10$ygi2nrBieAuAkqWV3SScfODDXzXBrg/arRjpEghgPIiDhq8iW21FK', 'Visitante 3', '76517721053', 'VISITANTE', NULL),
('visit4@evt.com', '$2a$10$znqK99AezTOt.at4Py3FW.tJedUjVbf0VQMQtljkJ97SvOUuLbbm.', 'Visitante 4', '06880102026', 'VISITANTE', NULL);

-- ('visit5@evt.com', '12', 'Visitante 5', '34255064040', 'VISITANTE', NULL),
-- ('visit6@evt.com', '123456', '', '81431178047', 'VISITANTE', NULL),
-- ('', '123456', 'Visitante 6', '88159106033', 'VISITANTE', NULL),
-- ('visit7@evt.com', '', 'Visitante 7', '94842759070', 'VISITANTE', NULL),
-- ('visit9@evt.com', '123456', 'Visitante 9', '2', 'VISITANTE', NULL),
-- ('visit8.com', '123456', 'Visitante 8', '06880102026', 'VISITANTE', NULL),
-- ('visit1@evt.com', '123456', 'Visitante 10', '69342515045', 'VISITANTE', NULL),
-- ('visit11@evt.com', '123456', 'Visitante 11', '07478267068', 'VISITANTE', NULL);







-- ============================================================
-- LOCAIS
-- ============================================================

INSERT INTO locais (nome, capacidade, rua, bairro, cidade, estado, numero, cep) VALUES
('Auditório Central', 150, 'Av. Paulista', 'Bela Vista', 'São Paulo', 'SP', 1000, '01310000'),
('Sala Tech', 50, 'Rua da Inovação', 'Centro', 'Campinas', 'SP', 500, '13000000'),
('Espaço Cultural', 200, 'Rua das Artes', 'Centro', 'Curitiba', 'PR', 150, '80000000'),
('Auditório Sul', 120, 'Av. Atlântica', 'Copacabana', 'Rio de Janeiro', 'RJ', 400, '22000000'),
('Sala de Reuniões A', 20, 'Rua Alfa', 'Centro', 'Belo Horizonte', 'MG', 50, '30000000'),
('Sala de Reuniões B', 25, 'Rua Beta', 'Centro', 'Belo Horizonte', 'MG', 51, '30000001'),
('Sala de Reuniões C', 65, 'Rua Gama', 'Vila Formosa', 'Belo Horizonte', 'MG', 85, '300'),
('Lab de Inovação', 40, 'Rua Tecnologia', 'TecnoPark', 'Campinas', 'SP', 12, '13050000'),
('Auditório Oeste', 180, 'Av. Brasil', 'Centro', 'Fortaleza', 'CE', 250, '60000000'),
('Centro de Convenções', 500, 'Av. das Nações', 'Asa Norte', 'Brasília', 'DF', 700, '70000000'),
-- ('Centro cultural', 501, 'Av. Romanov', 'Asa Sul', 'Brasília', 'DF', '', '70000001'),
('Espaço Coworking', 80, 'Rua Colaborativa', 'Moema', 'São Paulo', 'SP', 99, '04000000');

-- ============================================================
-- EVENTOS
-- ============================================================

INSERT INTO eventos (tipo_evento, estado_evento, data, descricao, titulo, vagas, local_id, palestrante_id) VALUES
('PRESENCIAL', 'ABERTO', '2025-12-01 09:00:00', 'Workshop de liderança', 'Workshop de Liderança', 30, 1, 1),
('REMOTO', 'ABERTO', '2025-12-05 10:00:00', 'Palestra sobre segurança da informação', 'Segurança Digital', 100, NULL, 2),
('HIBRIDO', 'ABERTO', '2025-12-10 15:00:00', 'Tech Conference 2025', 'Tech Conference', 200, 2, 3),
('PRESENCIAL', 'FECHADO', '2025-10-20 08:00:00', 'Reunião restrita de diretoria', 'Reunião Diretoria', 10, 5, 1),
('REMOTO', 'FECHADO', '2025-09-15 14:00:00', 'Treinamento remoto concluído', 'Treinamento Interno', 20, NULL, 2),
('HIBRIDO', 'ABERTO', '2025-12-22 18:00:00', 'Evento híbrido de marketing', 'Marketing Trends', 150, 3, 3),
('PRESENCIAL', 'ABERTO', '2025-12-30 09:00:00', 'Workshop para iniciantes', 'Primeiros Passos TI', 40, 7, 2),
('REMOTO', 'ABERTO', '2025-11-20 19:00:00', 'Palestra sobre produtividade', 'Alta Performance', 90, NULL, 1),
('HIBRIDO', 'FECHADO', '2025-09-10 13:00:00', 'Evento já encerrado', 'Encerramento 2025', 200, 8, 3),
('PRESENCIAL', 'ABERTO', '2025-12-25 10:00:00', 'Evento de Natal', 'Natal Solidário', 50, 9, 1);



-- ============================================================
-- PARTICIPAÇÕES
-- ============================================================

INSERT INTO participacoes_evento (evento_id, usuario_id) VALUES
-- válidos (somente eventos ABERTOS)
(1, 2), -- Ana no Workshop
(1, 7), -- Visitante 1
(2, 8), -- Visitante 2 no remoto
(3, 3), -- João na Tech Conf
(3, 4), -- Paula Aprendiz
(6, 5), -- Márcio no Marketing Trends
(7, 6), -- Juliana no Workshop TI
(8, 7), -- Visitante no Alta Performance
(10, 8), -- Visitante no Natal Solidário
(1, 9); -- Visitante 3 no Workshop

-- inválidos (para testes)
-- Evento fechado (não deveria ser permitido)
-- (4, 2),
-- (5, 3),
-- (9, 4);

