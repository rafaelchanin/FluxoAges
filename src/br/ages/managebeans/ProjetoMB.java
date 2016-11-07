package br.ages.managebeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ages.crud.model.ProjetoGitLab;
import br.ages.crud.model.UsuarioGitLab;

@ManagedBean
@SessionScoped
public class ProjetoMB {

	public List<ProjetoGitLab> getProjetosGitLAB() {
		Client c = Client.create();
		WebResource wr = c.resource(
				"http://www.tools.ages.pucrs.br/api/v3/projects/all?&private_token=cT5xMaSvdvxBrwvY4EFK&per_page=100");
		String json = wr.get(String.class);
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<ProjetoGitLab>>() {}.getType());
	}

	public List<UsuarioGitLab> getProjetosGitLABUsers() {
		Client c = Client.create();
		WebResource wr = c.resource(
				"http://www.tools.ages.pucrs.br/api/v3/users?&private_token=cT5xMaSvdvxBrwvY4EFK&per_page=100");
		String json = wr.get(String.class);
		Gson gson = new Gson();
		ArrayList<UsuarioGitLab> j =  gson.fromJson(json, new TypeToken<List<UsuarioGitLab>>() {}.getType());
		return j;
	}
}
