package br.ages.crud.model;

import org.mockito.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class TimePontoDTO{
	private int id;
	private int orientador;
	private Projeto projeto;
	private int semestre;
	private int ano;
	private String status;
	private ArrayList<ResumoPonto> pontos;
	private LocalDate primeiraAula;
	
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

	public LocalDate getPrimeiraAula() {
		return primeiraAula;
	}

	public void setPrimeiraAula(LocalDate primeiraAula) {
		this.primeiraAula = primeiraAula;
	}

	public ArrayList<ResumoPonto> getPontos() {
		return pontos;
	}
	
	public void setPontos(ArrayList<ResumoPonto> pontos) {
		this.pontos = pontos;
	}
	
	public String toString() {
		return ""+projeto.getNomeProjeto()+" / "+ano+"/"+semestre;
	}

}
