package br.ages.crud.model;

import java.util.ArrayList;
import java.util.Date;

public class Aula {
	private int id;
	private int idTurma;
	private Date data;
	private String status;
	private String observacao;
	private Date dtInclusao;
	private ArrayList<Presenca> presencas;
	
	public Aula(int id, int idTurma, Date data, String status, String observacao, Date dtInclusao) {
		this.id=id;
		this.setIdTurma(idTurma);
		this.data=data;
		this.status=status;
		this.observacao=observacao;
		this.dtInclusao=dtInclusao;
	}
	
	public Aula() {
		
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getObservacao() {
		return observacao;
	}
	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}
	public Date getDtInclusao() {
		return dtInclusao;
	}
	public void setDtInclusao(Date dtInclusao) {
		this.dtInclusao = dtInclusao;
	}
	public ArrayList<Presenca> getPresencas() {
		return presencas;
	}
	public void setPresencas(ArrayList<Presenca> presencas) {
		this.presencas = presencas;
	}

	public int getIdTurma() {
		return idTurma;
	}

	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}
	
	public String toString() {
		return "" + idTurma + data;
	}
	
}
