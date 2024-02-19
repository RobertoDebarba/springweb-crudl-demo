
DROP TABLE IF EXISTS login;
DROP TABLE IF EXISTS usuario;
DROP TABLE IF EXISTS pessoa;

-- Pessoa

CREATE TABLE pessoa (
  id int NOT NULL AUTO_INCREMENT,
  nome varchar(255) NOT NULL,
  idade int NOT NULL,

  PRIMARY KEY (id)
);

INSERT INTO pessoa VALUES (1,'Carlos',21),
                          (2,'Bruna',29),
                          (3,'Eliel',25),
                          (4,'Maria',74),
                          (5,'Vinicios',36);

-- Usuário

CREATE TABLE usuario (
  id int NOT NULL AUTO_INCREMENT,
  usuario varchar(255) NOT NULL,
  senha varchar(255) NOT NULL,

  PRIMARY KEY (id)
);

INSERT INTO usuario VALUES (1,'carlos','123'),
                           (2,'bruna','123'),
                           (3,'eliel','123'),
                           (4,'eliel2','123'),
                           (5,'maria','123');

-- Login

CREATE TABLE login (
   id int NOT NULL AUTO_INCREMENT,
   token varchar(36) NOT NULL,
   usuario int NOT NULL,

   PRIMARY KEY (id),
   CONSTRAINT fk_login_usuario1 FOREIGN KEY (usuario) REFERENCES usuario (id)
);

INSERT INTO login VALUES (1, '31c3f17b-1006-46b0-a806-f92eea608fff', 1),
                         (2, 'f70e434d-b51e-4911-9317-06ad01b1577c', 2);
