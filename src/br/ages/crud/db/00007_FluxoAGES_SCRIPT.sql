--liquibase formatted sql
--changeset cassio:7
--comment criação da api gitlab
  
ALTER TABLE `ages_e`.`tb_usuario` 
ADD COLUMN `USUARIO_GITLAB` VARCHAR(45) NULL DEFAULT NULL AFTER `EMAIL`;