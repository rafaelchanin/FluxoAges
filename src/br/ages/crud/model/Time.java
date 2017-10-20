package br.ages.crud.model;

import java.util.ArrayList;
import java.util.Date;

public class Time {
	private int id;
	private int orientador;
	private int projeto;
	private int semestre;
	private int ano;
	private String status;
	private Date dtInclusao;
	private ArrayList<Usuario> alunos;
	private Date primeiroDia;
	private Projeto projetoNome;

	public Time(int id, int orientador, int projeto, int semestre, int ano, String status, Date dtInclusao) {
		this.id = id;
		this.orientador = orientador;
		this.projeto = projeto;
		this.semestre = semestre;
		this.ano = ano;
		this.status = status;
		this.dtInclusao = dtInclusao;
	}

	public Time(int id, int orientador, int projeto, int semestre, int ano, String status, Date dtInclusao,
			ArrayList<Usuario> alunos) {
		this.id = id;
		this.orientador = orientador;
		this.projeto = projeto;
		this.semestre = semestre;
		this.ano = ano;
		this.status = status;
		this.dtInclusao = dtInclusao;
		this.alunos = alunos;
	}
	public Time() {
	}

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
	public int getProjeto() {
		return projeto;
	}
	public void setProjeto(int projeto) {
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
	public Date getDtInclusao() {
		return dtInclusao;
	}
	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}
	public void setPrimeiroDia(Date primeiroDia) {
		this.primeiroDia = primeiroDia;
	}
	public Date getPrimeiroDia() {
		return primeiroDia;
	}
	public ArrayList<Usuario> getAlunos() {
		return alunos;
	}
	public void setAlunos(ArrayList<Usuario> alunos) {
		this.alunos = alunos;
	}
	public Projeto getProjetoNome() {
		return projetoNome;
	}
	public void setProjetoNome(Projeto projetoNome) {
		this.projetoNome = projetoNome;
	}

	public String toString() {
		return ""+projetoNome.getNomeProjeto()+" / "+ano+"/"+semestre;
	}

}
