package br.ages.crud.model;

import java.time.LocalDate;
import java.util.Date;

public class Relatorio {

    private int idAluno;
    private int idRelatorio;
    private int idTime;
    private String atividadesPrevistas;
    private String atividadesConcluidas;
    private String licoesProblemas;
    private String proximo;
    private Date inicioSemana;
    private Date fimSemana;
    private Date dtInclusao;
    private StatusRelatorio status;

    public Relatorio(int idAluno, String atividadesPrevistas, String atividadesConcluidas,String proximo, String licoesProblemas, Date inicioSemana, Date fimSemana, StatusRelatorio status) {
        this.idAluno = idAluno;
        this.atividadesPrevistas = atividadesPrevistas;
        this.atividadesConcluidas = atividadesConcluidas;
        this.licoesProblemas = licoesProblemas;
        this.proximo = proximo;
        this.inicioSemana = inicioSemana;
        this.fimSemana = fimSemana;
        this.status = status;
    }

    public Relatorio(int idAluno, String atividadesPrevistas, String atividadesConcluidas, String licoesProblemas, StatusRelatorio status) {
        this.idAluno = idAluno;
        this.atividadesPrevistas = atividadesPrevistas;
        this.atividadesConcluidas = atividadesConcluidas;
        this.licoesProblemas = licoesProblemas;
        this.status = status;
    }

    public Relatorio() {
    }

    public int getIdAluno() {
        return idAluno;
    }

    public String getAtividadesPrevistas() {
        return atividadesPrevistas;
    }

    public int getIdTime() {
        return idTime;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public void setIdTime(int idTime) {
        this.idTime = idTime;
    }

    public int getIdRelatorio() {
        return idRelatorio;
    }

    public void setIdRelatorio(int idRelatorio) {
        this.idRelatorio = idRelatorio;
    }

    public String getAtividadesConcluidas() {
        return atividadesConcluidas;
    }

    public String getLicoesProblemas() {
        return licoesProblemas;
    }

    public Date getInicioSemana() {
        return inicioSemana;
    }

    public Date getFimSemana() {
        return fimSemana;
    }

    public void setIdAluno(int idAluno) {
        this.idAluno = idAluno;
    }

    public String getProximo() {
        return proximo;
    }

    public void setProximo(String proximo) {
        this.proximo = proximo;
    }

    public StatusRelatorio getStatus() {
        return status;
    }

    public void setStatus(StatusRelatorio status) {
        this.status = status;
    }

    public void setAtividadesPrevistas(String atividadesPrevistas) {
        this.atividadesPrevistas = atividadesPrevistas;
    }

    public void setAtividadesConcluidas(String atividadesConcluidas) {
        this.atividadesConcluidas = atividadesConcluidas;
    }

    public void setLicoesProblemas(String licoesProblemas) {
        this.licoesProblemas = licoesProblemas;
    }

    public void setInicioSemana(Date inicioSemana) {
        this.inicioSemana = inicioSemana;
    }

    public void setFimSemana(Date fimSemana) {
        this.fimSemana = fimSemana;
    }
}
