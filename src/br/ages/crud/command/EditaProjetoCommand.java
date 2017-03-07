package br.ages.crud.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import br.ages.crud.bo.ArquivoBO;
import br.ages.crud.bo.ProjetoBO;
import br.ages.crud.model.Projeto;
import br.ages.crud.model.Stakeholder;
import br.ages.crud.model.StatusProjeto;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.Constantes;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.util.Util;

public class EditaProjetoCommand implements Command{
	
	private ProjetoBO projetoBO;
	
	private ArquivoBO arquivoBO;
	
	private String proxima;

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		projetoBO =  new ProjetoBO();
		String idProjetoString = request.getParameter("idProjeto");
		//proxima = "project/editProject.jsp";
		proxima = "main?acao=telaProjeto&id_projeto=" + idProjetoString + "&isEdit=true";
		
		String nomeProjeto = request.getParameter("nomeProjeto");
		//String[] usuariosString = request.getParameterValues("listaUsuarios");
		String statusProjetoString = request.getParameter("statusProjeto");
		String[] stakeholdersString = request.getParameterValues("listaStakeholders");
		String workspace = request.getParameter("workspace");
		String dataInicioString = request.getParameter("dataInicio");
		String dataFimString = request.getParameter("dataFim");
		String dataFimPrevistoString = request.getParameter("dataFimPrevisto");
		
		try{
			Integer idProjeto = Integer.parseInt(idProjetoString);
			Projeto projeto = new Projeto();
			//ArrayList<Usuario> usuarios = new ArrayList<Usuario>();		
			/*for(String s: usuariosString){
				usuarios.add(new Usuario(Integer.parseInt(s)));
			}*/
			
			ArrayList<Stakeholder> stakeholders = new ArrayList<Stakeholder>();	
			if (stakeholdersString != null) {
				for(String s: stakeholdersString){
					if (!s.equals(""))
						stakeholders.add(new Stakeholder(Integer.parseInt(s)));
				}
				projeto.setStakeholders(stakeholders);
			}
			
			StatusProjeto statusProjeto = StatusProjeto.valueOf(statusProjetoString); 
			if (!dataInicioString.equals("")) {
				Date dataInicio = Util.stringToDate(dataInicioString);
				projeto.setDataInicio(dataInicio);
			}
			if (!dataFimPrevistoString.equals("")) {
				Date dataFimPrevisto = Util.stringToDate(dataFimPrevistoString);
				projeto.setDataFimPrevisto(dataFimPrevisto);
			}
			if (!dataFimString.equals("")) {
				Date dataFim = dataFimString.equals("") ? null : Util.stringToDate(dataFimString);
				projeto.setDataFim(dataFim);
			}
			
			
			
			projeto.setIdProjeto(idProjeto);
			projeto.setNomeProjeto(nomeProjeto);
			//projeto.setUsuarios(usuarios);
			projeto.setStatusProjeto(statusProjeto);
			projeto.setWorkspace(workspace);
			
			

			boolean isValido = projetoBO.validarProjeto(projeto);

			if(isValido){
				projetoBO.editarProjeto(projeto);
				proxima = "main?acao=listaProjetos";
				request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_EDICAO_PROJETO.replace("?", projeto.getNomeProjeto()));

			} else {
				request.setAttribute("msgErro", MensagemContantes.MSG_ERR_PROJETO_DADOS_INVALIDOS);
			}
		}catch(Exception e){
			request.setAttribute("msgErro", e.getMessage());
		}		
		
		return proxima;		
	}

}
