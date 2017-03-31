package br.ages.crud.model;

import java.io.Serializable;
import java.util.Date;

public class ResumoPonto implements Serializable {

	private static final long serialVersionUID = -8690624546267273422L;
	private int idPonto;
	private String nomeAluno;
	private int idAluno;
	private Date dataEtrada;
	private int minutoTotalDia;
	private int minutoTotalDiaInvalido;
	private int minutoTotalDiaValido;
	
	public ResumoPonto() {
	}
	
	public ResumoPonto(int idPonto, String nomeAluno, Date dataEtrada, int horaTotalDia) {
		super();
		this.idPonto = idPonto;
		this.nomeAluno = nomeAluno;
		this.dataEtrada = dataEtrada;
		this.minutoTotalDia = horaTotalDia;
	}
	
	public int getIdPonto() {
		return idPonto;
	}
	
	public void setIdPonto(int idPonto) {
		this.idPonto = idPonto;
	}

	public String getNomeAluno() {
		return nomeAluno;
	}

	public void setNomeAluno(String nomeAluno) {
		this.nomeAluno = nomeAluno;
	}

	public Date getDataEtrada() {
		return dataEtrada;
	}

	public void setDataEtrada(Date dataEtrada) {
		this.dataEtrada = dataEtrada;
	}

	public int getHoraTotalDia() {
		return minutoTotalDia;
	}

	public void setHoraTotalDia(int horaEntrada) {
		this.minutoTotalDia = horaEntrada;
	}

	@Override
	public String toString() {
		return "ResumoPonto [idPonto=" + idPonto + ", nomeAluno=" + nomeAluno + ", dataEtrada=" + dataEtrada + ", horaTotalDia=" + minutoTotalDia + "]";
	}

	public int getHoraTotalDiaInvalido() {
		return minutoTotalDiaInvalido;
	}

	public void setHoraTotalDiaInvalido(int horaTotalDiaInvalido) {
		this.minutoTotalDiaInvalido = horaTotalDiaInvalido;
	}

	public int getHoraTotalDiaValido() {
		return minutoTotalDiaValido;
	}

	public void setHoraTotalDiaValido(int horaTotalDiaValido) {
		this.minutoTotalDiaValido = horaTotalDiaValido;
	}

	public int getIdAluno() {
		return idAluno;
	}

	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}

	
}
