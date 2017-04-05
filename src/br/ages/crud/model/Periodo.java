package br.ages.crud.model;

public class Periodo {
	private int id;
	private String horario;
	private String horaInicio;
	private String horaFim;
	private int tempo;

	public Periodo() {
		
	}
	
	public Periodo(int id, String horario, String horaInicio, String horaFim, int tempo) {
		this.id=id;
		this.horario=horario;
		this.horaInicio=horaInicio;
		this.horaFim=horaFim;
		this.tempo=tempo;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getHorario() {
		return horario;
	}
	public void setHorario(String horario) {
		this.horario = horario;
	}
	public String getHoraInicio() {
		return horaInicio;
	}
	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}
	public String getHoraFim() {
		return horaFim;
	}
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
}
