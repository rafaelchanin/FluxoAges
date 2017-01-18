package br.ages.crud.model;

public class Presenca {
	private int idAula;
	private Usuario aluno;
	private int idTurma;
	private String status;
	
	public Presenca() {
		
	}
	
	public Presenca(int idAula, Usuario aluno, int idTurma, String status) {
		this.idAula=idAula;
		this.aluno=aluno;
		this.idTurma=idTurma;
		this.status=status;
	}
	
	public int getIdAula() {
		return idAula;
	}
	public void setIdAula(int idAula) {
		this.idAula = idAula;
	}
	
	public Usuario getAluno() {
		return aluno;
	}

	public void setAluno(Usuario aluno) {
		this.aluno = aluno;
	}

	public int getIdTurma() {
		return idTurma;
	}
	public void setIdTurma(int idTurma) {
		this.idTurma = idTurma;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
