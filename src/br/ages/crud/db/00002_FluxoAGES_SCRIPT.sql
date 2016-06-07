--liquibase formatted sql
--changeset cassio:2

/***
* Cria��o das tablesa para avalia��o de SKILLs e
* Altera��o da tabela tb_tipo_usuario 
* 
* Casssio Trindade
* 06/2016
* 
***/

-- alter table tb_tipo_usuario
ALTER TABLE `ages_e`.`tb_tipo_usuario` 
ADD COLUMN `FLAG_RESPONSAVEL` VARCHAR(1) NULL COMMENT '' AFTER `DATA_INCLUSAO`;

-- table tb_skills para armazenar as avalia��es dos alunos
CREATE TABLE `tb_skills` (
  `ID_SKILLS` int(11) NOT NULL,
  `ID_DEFINICAO` varchar(45) DEFAULT NULL,
  `VALOR` int(1) DEFAULT NULL,
  `DATA_VALOR` datetime DEFAULT NULL,
  `OBSERVACAO` varchar(145) DEFAULT NULL,
  `ID_USUARIO_ALUNO` int(11) DEFAULT NULL,
  `ID_USUARIO_AVALIADOR` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID_SKILLS`),
  KEY `FK_USUARIO_ALUNO_idx` (`ID_USUARIO_ALUNO`),
  KEY `FK_USUARIO_AVALIADOR_idx` (`ID_USUARIO_AVALIADOR`),
  CONSTRAINT `FK_DEFINICAO_SKILL` FOREIGN KEY (`ID_SKILLS`) REFERENCES `tb_skills_definicao` (`ID_SKILLS_DEFINICAO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_USUARIO_ALUNO` FOREIGN KEY (`ID_USUARIO_ALUNO`) REFERENCES `tb_usuario` (`ID_USUARIO`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_USUARIO_AVALIADOR` FOREIGN KEY (`ID_USUARIO_AVALIADOR`) REFERENCES `tb_usuario` (`ID_USUARIO`) ON DELETE NO ACTION ON UPDATE NO ACTION
);

-- table tb_skills_definicao armazena a defini��o de quad tipo de skill.
CREATE TABLE `tb_skills_definicao` (
  `ID_SKILLS_DEFINICAO` int(11) NOT NULL,
  `TIPO` varchar(20) DEFAULT NULL,
  `NOME` varchar(45) DEFAULT NULL,
  `DESCRICAO` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`ID_SKILLS_DEFINICAO`)
);

