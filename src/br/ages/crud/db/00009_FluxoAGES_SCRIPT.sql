--liquibase formatted sql
--changeset andre:9
--comment garante que o nome, da tabela tb_usuario, nao pode se repeti

ALTER TABLE tb_usuario ADD CONSTRAINT uq_nome_usuario UNIQUE (NOME);