package br.ages.crud.model;

public class Presenca {
	private int idAula;
	private int idAluno;
	private String matriculaAluno;
	private int idTurma;
	private String status;
	
	public Presenca() {
		
	}
	
	public Presenca(int idAula, int idAluno, String matriculaAluno, int idTurma, String status) {
		this.idAula=idAula;
		this.idAluno=idAluno;
		this.matriculaAluno=matriculaAluno;
		this.idTurma=idTurma;
		this.status=status;
	}
	
	public int getIdAula() {
		return idAula;
	}
	public void setIdAula(int idAula) {
		this.idAula = idAula;
	}
	public int getIdAluno() {
		return idAluno;
	}
	public void setIdAluno(int idAluno) {
		this.idAluno = idAluno;
	}
	public String getMatriculaAluno() {
		return matriculaAluno;
	}
	public void setMatriculaAluno(String matriculaAluno) {
		this.matriculaAluno = matriculaAluno;
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
