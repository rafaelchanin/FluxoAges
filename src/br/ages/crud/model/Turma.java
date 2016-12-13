package br.ages.crud.model;

import java.util.ArrayList;
import java.util.Date;

public class Turma {
	private int numero;
	private String status;
	private int ages;
	private int semestre;
	private int ano;
	private Date dtInclusao;
	private ArrayList<Usuario> alunos;
	
	public Turma(int numero, String status, int ages, int semestre, int ano, Date dtInclusao) {
		this.numero=numero;
		this.status=status;
		this.ages=ages;
		this.semestre=semestre;
		this.ano=ano;
		this.dtInclusao=dtInclusao;
		alunos = new ArrayList<>();
	}
	
	public Turma(int numero, String status, int ages, int semestre, int ano, Date dtInclusao, ArrayList<Usuario> alunos) {
		this.numero=numero;
		this.status=status;
		this.ages=ages;
		this.semestre=semestre;
		this.ano=ano;
		this.dtInclusao=dtInclusao;
		this.alunos=alunos;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAges() {
		return ages;
	}

	public void setAges(int ages) {
		this.ages = ages;
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

	public Date getDtInclusao() {
		return dtInclusao;
	}

	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}

	public ArrayList<Usuario> getAlunos() {
		return alunos;
	}

	public void setAlunos(ArrayList<Usuario> alunos) {
		this.alunos = alunos;
	}
	
	
}
