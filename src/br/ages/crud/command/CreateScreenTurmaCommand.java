package br.ages.crud.command;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.ProjetoBO;
import br.ages.crud.bo.StakeholderBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.IdNomeUsuarioDTO;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Projeto;
import br.ages.crud.model.Stakeholder;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

public class CreateScreenTurmaCommand implements Command {

	private String proxima;
	
	private ProjetoBO projetoBO;
	private TurmaBO turmaBO;
	private UsuarioBO usuarioBO;
	private StakeholderBO stakeholderBO;

	@Override
	public String execute(HttpServletRequest request) throws SQLException, NegocioException {
		//TODO utilizar stakeholderBO.listaStakeholders ap�s a implementa��o do mesmo
		proxima = "main?acao=listaTurmas";
		Usuario currentUser = (Usuario)request.getSession().getAttribute("usuarioSessao");		
		
		try{
			
			if( !currentUser.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);
			String isEdit = request.getParameter("isEdit");
			
			if (isEdit != null && !"".equals(isEdit)) {
				proxima = "turma/editTurma.jsp";
				projetoBO = new ProjetoBO();
				usuarioBO = new UsuarioBO();
				int idProjeto = Integer.parseInt(request.getParameter("id_projeto"));
				Projeto projeto = projetoBO.buscarProjeto(idProjeto);
				
				List<Usuario> usuarioProjeto = projeto.getUsuarios();
				List<Usuario> usuarios = usuarioBO.listarUsuario();

				
				for(int i = 0; i < usuarioProjeto.size(); i++){
					for(int j = 0; j < usuarios.size(); j++){
						if(usuarios.get(j).getIdUsuario() == usuarioProjeto.get(i).getIdUsuario()){
							usuarios.remove(j);
							break;
						}
					}
				}
				
				request.setAttribute("projeto", projeto);
				request.setAttribute("alunos", usuarios);
			//	request.setAttribute("listaStakeholders", stakeholders);
				
				
			} else {
				//TODO implementar StakeholderBO e DAO pra fazer essa parte
				proxima = "turma/addTurma.jsp";
		
				usuarioBO = new UsuarioBO();
				List<IdNomeUsuarioDTO> alunos = usuarioBO.alunosElegiveis();				
						
				request.setAttribute("alunos", alunos);
			}
		} catch(Exception e){
			request.setAttribute("msgErro", e.getMessage());
		}
		
		return proxima;
	}
	
	
}
