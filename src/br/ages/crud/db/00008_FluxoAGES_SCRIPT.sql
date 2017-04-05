--liquibase formatted sql
--changeset andre:8
--comment tabela periodo ja com os horarios definidos e adiciona a coluna de periodo na aula
  
CREATE TABLE tb_periodo (
ID_PERIODO int(11) NOT NULL AUTO_INCREMENT,
HORARIO varchar(2) DEFAULT NULL,
HORA_INICIO time NOT NULL,
HORA_FIM time NOT NULL,
TEMPO int(11) NOT NULL,
PRIMARY KEY (ID_PERIODO)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

ALTER TABLE tb_aula 
	ADD COLUMN ID_PERIODO int(11) DEFAULT NULL,
	ADD CONSTRAINT fk_periodo_u FOREIGN KEY (ID_PERIODO) REFERENCES tb_periodo (ID_PERIODO) ON DELETE NO ACTION ON UPDATE NO ACTION
;

INSERT INTO tb_periodo VALUES (20, "JK", "17:30:00", "19:00:00", 120);
INSERT INTO tb_periodo VALUES (21, "LM", "19:30:00", "21:00:00", 120);
INSERT INTO tb_periodo VALUES (22, "NP", "21:15:00", "22:45:00", 120);