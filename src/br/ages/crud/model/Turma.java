package br.ages.crud.model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.ages.crud.util.Util;

public class Turma{
	

	private int id;
	private int numero;
	private String status;
	private String presencas;
	private int ages;
	private int semestre;
	private int ano;
	private Date dtInclusao;
	private ArrayList<Usuario> alunos;
	private ArrayList<Aula> aulas;
	
	public Turma(int numero, String status, int ages, int semestre, int ano, Date dtInclusao) {
		this.numero=numero;
		this.status=status;
		this.ages=ages;
		this.semestre=semestre;
		this.ano=ano;
		this.dtInclusao=dtInclusao;
		alunos = new ArrayList<>();
		aulas = new ArrayList<>();
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

	public Turma() {
		// TODO Auto-generated constructor stub
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Aula> getAulas() {
		return aulas;
	}

	public String getAulasString() {
		String date = "";
		if (aulas == null) {
			return "";
		}
		else {
			for (Aula aula : aulas){
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String data;
				data = formatter.format(aula.getData());
				//date = date + "," + aula.getData().toString();
				if (date.equals("")) 
					date=aula.getId() + ":" + data;
				else
					date = date + "," + aula.getId() + ":" + data;
			}
			return date;
		}
	}
	
	public String getAulasPresencaString() {
		String date = "";
		if (aulas == null) {
			return "";
		}
		else {
			for (Aula aula : aulas){
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String data;
				if (aula.getPresencas().size() != 0) {
					data = formatter.format(aula.getData());
				
					if (date.equals("")) 
						date=aula.getId() + ":" + data;
					else
						date = date + "," + aula.getId() + ":" + data;
				}
			}
			return date;
		}
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
	
	public void setAulas(ArrayList<Aula> aulas) {
		this.aulas = aulas;
	}
	
	@Override
	public String toString() {
		return ano + " / " + semestre + " - AGES " + ages +" - " + numero;
	}

	public String getPresencas() {
		return presencas;
	}

	public void setPresencas(String presencas) {
		this.presencas = presencas;
	}
	
	
}
