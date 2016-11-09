package br.ages.managebeans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.gitlab.api.GitlabAPI;
import org.gitlab.api.models.GitlabCommit;
import org.gitlab.api.models.GitlabGroup;
import org.gitlab.api.models.GitlabProject;
import org.primefaces.model.chart.PieChartModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;

import br.ages.crud.model.ProjetoGitLab;
import br.ages.crud.model.UsuarioGitLab;
import br.ages.crud.util.Constantes;

@ManagedBean
@SessionScoped
public class ProjetoMB implements Serializable {
	

	private static final long serialVersionUID = -8237341916112634224L;
	private GitlabAPI api;
	private PieChartModel pieModel;
	
	public ProjetoMB() {
		api = GitlabAPI.connect(Constantes.GITLAB_URL, Constantes.GITLAB_TOKEN);
	}
	
	@PostConstruct
    public void init(){
		createPieModel();
		
	}
    

	public List<ProjetoGitLab> getProjetosGitLAB() {
		Client c = Client.create();
		WebResource wr = c.resource(
				"http://www.tools.ages.pucrs.br/api/v3/projects/all?&private_token=cT5xMaSvdvxBrwvY4EFK&per_page=100");
		String json = wr.get(String.class);
		Gson gson = new Gson();
		return gson.fromJson(json, new TypeToken<List<ProjetoGitLab>>() {
		}.getType());
	}

	public List<UsuarioGitLab> getProjetosGitLABUsers() {

		int idPage = 1;
		Client c = Client.create();
		WebResource wr = c.resource(
				"http://www.tools.ages.pucrs.br/api/v3/users?&private_token=cT5xMaSvdvxBrwvY4EFK&per_page=25&page="
						+ idPage);
		String json = wr.get(String.class);
		Gson gson = new Gson();
		ArrayList<UsuarioGitLab> j = gson.fromJson(json, new TypeToken<List<UsuarioGitLab>>() {
		}.getType());
		return j;
	}

	public List<GitlabProject> getGitLabProjects() {
		List<GitlabProject> gitlabProjects = null;
		
		try {
			gitlabProjects = api.getAllProjects();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return gitlabProjects;

	}
	public List<GitlabGroup> getGitLabGroups() {
		List<GitlabGroup> gitlabGroups = null;
	
		try {
			gitlabGroups = api.getGroups();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return gitlabGroups;
		
	}
	public List<GitlabCommit> getGitLabCommits() {
		List<GitlabCommit> gitlabCommits = null;
		
		try {
			gitlabCommits = api.getAllCommits(15);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return gitlabCommits;
		
	}
	
	public PieChartModel getPieModel() {
        return pieModel;
    }
     
	
	private void createPieModel() {
        pieModel = new PieChartModel();
        
        
        
      //  getGitLabCommits().forEach(g -> pieModel.set(g.getAuthorName(), Integer.parseInt(g.getId())));
        
        pieModel.set("Brand 1", 540);
        pieModel.set("Brand 2", 325);
        pieModel.set("Brand 3", 702);
        pieModel.set("Brand 4", 421);
        
        pieModel.setTitle("Commits");
        pieModel.setLegendPosition("w");
    }
}
