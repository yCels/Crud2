create database crud2;

use  crud2;

CREATE TABLE curso (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    carga_horaria INT NOT NULL,
    limite_alunos INT NOT NULL,
    ativo BOOLEAN DEFAULT TRUE
);

CREATE TABLE aluno (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(11) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL,
    data_nascimento DATE NOT NULL,
    id_curso INT,
    ativo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_curso) REFERENCES curso(id) ON DELETE CASCADE
);