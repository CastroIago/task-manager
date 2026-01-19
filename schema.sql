-- Criação da tabela de Usuários
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

-- Criação da tabela de Tarefas
CREATE TABLE tasks (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(255) NOT NULL,
                       description TEXT,
                       status VARCHAR(50),
                       user_id BIGINT,
                       CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);