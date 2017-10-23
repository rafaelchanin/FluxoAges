package br.ages.crud.model;

import java.util.ArrayList;
import java.util.Date;

public class Grupo{

    private int id;
    private String status;
    private String presencas;
    private String projeto;
    private int semestre;
    private int ano;
    private Date dtInclusao;
    private ArrayList<Usuario> alunos;

    public Grupo(String status, String projeto, int semestre, int ano, Date dtInclusao) {
        this.status=status;
        this.projeto=projeto;
        this.semestre=semestre;
        this.ano=ano;
        this.dtInclusao=dtInclusao;
        alunos = new ArrayList<>();
    }

    public Grupo(String status, String projeto, int semestre, int ano, Date dtInclusao, ArrayList<Usuario> alunos) {
        this.status=status;
        this.projeto=projeto;
        this.semestre=semestre;
        this.ano=ano;
        this.dtInclusao=dtInclusao;
        this.alunos=alunos;
    }

    public Grupo(){}

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getPresencas() {
        return presencas;
    }

    public String getProjeto() {
        return projeto;
    }

    public int getSemestre() {
        return semestre;
    }

    public int getAno() {
        return ano;
    }

    public Date getDtInclusao() {
        return dtInclusao;
    }

    public ArrayList<Usuario> getAlunos() {
        return alunos;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPresencas(String presencas) {
        this.presencas = presencas;
    }

    public void setProjeto(String projeto) {
        this.projeto = projeto;
    }

    public void setSemestre(int semestre) {
        this.semestre = semestre;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setDtInclusao(Date dtInclusao) {
        this.dtInclusao = dtInclusao;
    }

    public void setAlunos(ArrayList<Usuario> alunos) {
        this.alunos = alunos;
    }

    public String getAlunosString() {
        String alunosString = "";
        if (alunos == null) {
            return "";
        }
        else {
            for (Usuario aluno : alunos){
                //date = date + "," + aula.getData().toString();
                if (alunosString.equals(""))
                    alunosString=aluno.getIdUsuario() + "-" + aluno.getMatricula() + ":" + aluno.getNome();
                else
                    alunosString = alunosString + "," + aluno.getIdUsuario() + "-" + aluno.getMatricula() + ":" + aluno.getNome();
            }
            return alunosString;
        }
    }

    public String toString() {
        return ano + " / " + semestre + " - AGES " + projeto;
    }
}
