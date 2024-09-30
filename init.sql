-- Criação da tabela Avaliacao
CREATE TABLE Avaliacao (
    id SERIAL PRIMARY KEY,
    descricao TEXT,
    nota INTEGER CHECK (nota BETWEEN 0 AND 5),
    tipo VARCHAR(50)
);

-- Criação da tabela Servico
CREATE TABLE Servico (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Criação da tabela Profissional
CREATE TABLE Profissional (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    horarios_disponiveis VARCHAR(255),
    tarifas NUMERIC(10, 2),
    avaliacao_id INT,
    servico_id INT,
    CONSTRAINT fk_avaliacao
        FOREIGN KEY (avaliacao_id)
        REFERENCES Avaliacao(id),
    CONSTRAINT fk_servico
        FOREIGN KEY (servico_id)
        REFERENCES Servico(id)
);


CREATE TABLE Cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL
);

-- Criação da tabela Estabelecimento
CREATE TABLE Estabelecimento (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    endereco VARCHAR(255) NOT NULL,
    horario_funcionamento VARCHAR(255),
    fotos TEXT[],
    avaliacao_id INT,
    profissional_id INT,
    CONSTRAINT fk_avaliacao
        FOREIGN KEY (avaliacao_id)
        REFERENCES Avaliacao(id),
    CONSTRAINT fk_profissional
        FOREIGN KEY (profissional_id)
        REFERENCES Profissional(id)
);

-- Tabela de relacionamento muitos-para-muitos entre Estabelecimento e Servico
CREATE TABLE Estabelecimento_Servico (
    estabelecimento_id INT,
    servico_id INT,
    PRIMARY KEY (estabelecimento_id, servico_id),
    CONSTRAINT fk_estabelecimento
        FOREIGN KEY (estabelecimento_id)
        REFERENCES Estabelecimento(id),
    CONSTRAINT fk_servico
        FOREIGN KEY (servico_id)
        REFERENCES Servico(id)
);

-- Relacionamento muitos-para-muitos entre Estabelecimento e Profissional
CREATE TABLE Estabelecimento_Profissional (
    estabelecimento_id INT,
    profissional_id INT,
    PRIMARY KEY (estabelecimento_id, profissional_id),
    CONSTRAINT fk_estabelecimento
        FOREIGN KEY (estabelecimento_id)
        REFERENCES Estabelecimento(id),
    CONSTRAINT fk_profissional
        FOREIGN KEY (profissional_id)
        REFERENCES Profissional(id)
);

-- Criação da tabela Agendamento
CREATE TABLE Agendamento (
    id SERIAL PRIMARY KEY,
    estabelecimento_id INT NOT NULL,
    profissional_id INT NOT NULL,
    cliente_id INT NOT NULL,
    status VARCHAR(50),
    data_agendamento DATE NOT NULL,
    hora_agendamento TIME NOT NULL,
    CONSTRAINT fk_estabelecimento
        FOREIGN KEY (estabelecimento_id)
        REFERENCES Estabelecimento(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_cliente
        FOREIGN KEY (cliente_id)
        REFERENCES Cliente(id)
        ON DELETE CASCADE,
    CONSTRAINT fk_profissional
        FOREIGN KEY (profissional_id)
        REFERENCES Profissional(id)
        ON DELETE CASCADE
);


-- Inserção de dados iniciais na tabela Servico
INSERT INTO Servico (nome) VALUES
('Corte de Cabelo'),
('Coloração'),
('Design de Sobrancelhas'),
('Massagem'),
('Maquiagem');

-- Inserção de dados iniciais na tabela Avaliacao
INSERT INTO Avaliacao (descricao, nota, tipo) VALUES
('Ótimo serviço', 5, 'Cliente'),
('Bom atendimento', 4, 'Cliente'),
('Pode melhorar', 3, 'Cliente');

-- Inserção de dados iniciais na tabela Profissional
INSERT INTO Profissional (id, nome, horarios_disponiveis, tarifas, avaliacao_id, servico_id) VALUES
(1, 'Ana', 'Segunda Sexta 9:00-19:00', 150.00, 1, 1),
(2, 'Carlos', 'Segunda Sexta 9:00-19:00', 100.00, 2, 3),
(3, 'Martha', 'Quarta Domingo 9:00-22:00', 120.00, 2, 2),
(4, 'Gabriel', 'Terça Sábado 12:00-21:00', 300.00, 2, 3),
(5, 'Jorge', 'Segunda Sexta 9:00-19:00', 50.00, 2, 4);

INSERT INTO Cliente (id, nome) VALUES
(1, 'Matheus'),
(2, 'Flavia');

-- Inserção de dados iniciais na tabela Estabelecimento
INSERT INTO Estabelecimento (id, nome, endereco, horario_funcionamento, fotos, avaliacao_id) VALUES
(1,'Salão Bela Vida', 'Rua das Flores, 123', 'Segunda Sexta 9:00-19:00', ARRAY['foto1.jpg', 'foto2.jpg'], 1),
(2, 'Studio Beleza', 'Avenida Central, 456', 'Terça Sábado 10:00-20:00', ARRAY['foto3.jpg'], 2),
(3, 'Spa Vida', 'Avenida Cascao, 123', 'Quarta Domingo 9:00-22:00', ARRAY['foto3.jpg'], 3),
(4, 'Massagens SA', 'Avenida Brasil, 32', 'Terça Sábado 12:00-21:00', ARRAY['foto3.jpg'], 3);

-- Inserção de dados iniciais na tabela Estabelecimento_Servico
INSERT INTO Estabelecimento_Servico (estabelecimento_id, servico_id) VALUES
(1, 1), 
(1, 2), 
(3, 3), 
(3, 4), 
(2, 3), 
(4, 2),
(4, 1),
(2, 4); 

-- Inserção de dados iniciais na tabela Estabelecimento_pro
INSERT INTO Estabelecimento_Profissional (estabelecimento_id, profissional_id) VALUES
(1, 1), 
(1, 2), 
(2, 3),
(3, 3), 
(4, 1), 
(4, 4), 
(2, 4); 

-- Inserção de dados iniciais na tabela Agendamento
INSERT INTO Agendamento (estabelecimento_id, profissional_id, cliente_id, status, data_agendamento, hora_agendamento) VALUES
(1, 1, 1, 'AGENDADO', '2024-09-15', '10:00'),
(2, 2, 2, 'CANCELADO', '2024-09-16', '14:00');
