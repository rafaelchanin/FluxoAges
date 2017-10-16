package br.ages.crud.model;

import java.time.LocalDate;
import java.util.Date;

public class Relatorio {

    private int idRelatorio;
    private int idTimeAluno;
    private String atividadesPrevistas;
    private String atividadesConcluidas;
    private String licoesProblemas;
    private String proximo;
    private Date inicioSemana;
    private Date dtInclusao;
    private StatusRelatorio status;
    private TipoRelatorio tipo;

    public Relatorio( String atividadesPrevistas, String atividadesConcluidas,String proximo, String licoesProblemas, Date inicioSemana, StatusRelatorio status) {
        this.atividadesPrevistas = atividadesPrevistas;
        this.atividadesConcluidas = atividadesConcluidas;
        this.licoesProblemas = licoesProblemas;
        this.proximo = proximo;
        this.inicioSemana = inicioSemana;
        this.status = status;
    }

    public Relatorio(String atividadesPrevistas, String atividadesConcluidas, String licoesProblemas, StatusRelatorio status) {
        this.atividadesPrevistas = atividadesPrevistas;
        this.atividadesConcluidas = atividadesConcluidas;
        this.licoesProblemas = licoesProblemas;
        this.status = status;
    }

    public Relatorio() {
    }


    public String getAtividadesPrevistas() {
        return atividadesPrevistas;
    }

    public int getIdTimeAluno() {
        return idTimeAluno;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public TipoRelatorio getTipo() {
        return tipo;
    }

    public void setTipo(TipoRelatorio tipo) {
        this.tipo = tipo;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public void setIdTimeAluno(int idTimeAluno) {
        this.idTimeAluno = idTimeAluno;
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

}
