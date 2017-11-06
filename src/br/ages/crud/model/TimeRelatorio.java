package br.ages.crud.model;

import java.util.ArrayList;

public class TimeRelatorio {
    private int id;
    private int orientador;
    private Projeto projeto;
    private int semestre;
    private int ano;
    private String status;
    private ArrayList<Relatorio> relatorio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrientador() {
        return orientador;
    }

    public void setOrientador(int orientador) {
        this.orientador = orientador;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public int getSemestre() {
        return semestre;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Relatorio> getRelatorio() {
        return relatorio;
    }

    public void setRelatorio(ArrayList<Relatorio> relatorio) {
        this.relatorio = relatorio;
    }

    public String toString() {
        return ""+projeto.getNomeProjeto()+" / "+ano+"/"+semestre;
    }
}
