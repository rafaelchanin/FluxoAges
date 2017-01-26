package br.ages.crud.model;

public class IdNomeUsuarioDTO {
	private int id;
	private String nome;
	private String matricula;
	
	public IdNomeUsuarioDTO (int id, String nome) {
		this.id=id;
		this.nome=nome;
	}
	
	public IdNomeUsuarioDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	
}
