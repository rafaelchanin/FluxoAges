-- --liquibase formatted sql
-- --changeset cássio:4
-- --comment Atualizacao tamanho do campo senha para implementar a criptografia do valor.

USE ages_e;


ALTER TABLE TB_USUARIO  CHANGE COLUMN SENHA SENHA VARCHAR(120) NOT NULL COMMENT '' ;