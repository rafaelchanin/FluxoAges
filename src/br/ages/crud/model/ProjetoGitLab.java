package br.ages.crud.model;

import java.io.Serializable;

public class ProjetoGitLab implements Serializable{
	/**
	 * 
	 */
	
	private int id;
	private String web_url;
	private String name;

	public ProjetoGitLab() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWeb_url() {
		return web_url;
	}

	public void setWeb_url(String web_url) {
		this.web_url = web_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
