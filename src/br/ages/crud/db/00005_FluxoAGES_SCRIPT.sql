--liquibase formatted sql
--changeset andre:5
--comment Correcao tb_tipo_usuario; Turma; Times

ALTER TABLE `ages_e`.`tb_usuario` 
ADD CONSTRAINT `fk_tipo_usuario`
  FOREIGN KEY (`id_tipo_usuario`)
  REFERENCES `ages_e`.`tb_tipo_usuario` (`ID_TIPO_USUARIO`);
  
CREATE TABLE tb_time (
ID_TIME int(11) NOT NULL AUTO_INCREMENT,
ID_ORIENTADOR int(11) NOT NULL,
ID_PROJETO int(11) NOT NULL,
SEMESTRE int(1) NOT NULL,
ANO int(4) NOT NULL,
STATUS_TIME varchar(20) NOT NULL,
DT_INCLUSAO datetime DEFAULT NULL,
PRIMARY KEY (ID_TIME),
KEY fk_time_idx (ID_TIME),
  CONSTRAINT fk_orientador_u FOREIGN KEY (ID_ORIENTADOR) REFERENCES tb_usuario (ID_USUARIO) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_projeto_u FOREIGN KEY (ID_PROJETO) REFERENCES tb_projeto (ID_PROJETO) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

CREATE TABLE tb_turma (
ID_TURMA int(11) NOT NULL AUTO_INCREMENT,
NUMERO int(5) NOT NULL,
STATUS_TURMA varchar(20) NOT NULL,
AGES int(1) NOT NULL,
SEMESTRE int(1) NOT NULL,
ANO int(4) NOT NULL,
DT_INCLUSAO datetime DEFAULT NULL, 
PRIMARY KEY (ID_TURMA)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

CREATE TABLE tb_turma_aluno (
ID_TURMA int(11) NOT NULL,
ID_ALUNO int(11) NOT NULL,
PRIMARY KEY (ID_TURMA,ID_ALUNO),
KEY fk_turma_idx (ID_TURMA),
  CONSTRAINT fk_turma_u FOREIGN KEY (ID_TURMA) REFERENCES tb_turma (ID_TURMA) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_aluno_u FOREIGN KEY (ID_ALUNO) REFERENCES tb_usuario (ID_USUARIO) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE tb_time_aluno (
ID_TIME int(11) NOT NULL,
ID_ALUNO int(11) NOT NULL,
PRIMARY KEY (ID_TIME,ID_ALUNO),
KEY fk_timetu_idx (ID_TIME),
  CONSTRAINT fk_time_tu FOREIGN KEY (ID_TIME) REFERENCES tb_time (ID_TIME) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT fk_aluno_tu FOREIGN KEY (ID_ALUNO) REFERENCES tb_usuario (ID_USUARIO) ON DELETE NO ACTION ON UPDATE NO ACTION
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  
ALTER TABLE `ages_e`.`TB_USUARIO` 
ADD COLUMN `USUARIO_GITLAB` VARCHAR(45) NULL DEFAULT NULL AFTER `EMAIL`;