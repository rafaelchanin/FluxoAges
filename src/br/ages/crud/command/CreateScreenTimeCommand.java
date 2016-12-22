package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.TimeBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.IdNomeUsuarioDTO;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Time;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

public class CreateScreenTimeCommand implements Command {
	private String proxima;
	private TimeBO timeBO;
	private UsuarioBO usuarioBO;
	@Override
	public String execute(HttpServletRequest request) throws SQLException, NegocioException {
		proxima = "main?acao=listaTimes";
		Usuario currentUser = (Usuario)request.getSession().getAttribute("usuarioSessao");		

		try{

			if( !currentUser.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);
			String isEdit = request.getParameter("isEdit");

			if (isEdit != null && !"".equals(isEdit)) {
				proxima = "turma/editTurma.jsp";
				timeBO = new TimeBO();
				usuarioBO = new UsuarioBO();
				int idTime = Integer.parseInt(request.getParameter("id_turma"));
				Time time = timeBO.buscarTime(idTime);

				List<IdNomeUsuarioDTO> alunosProjeto = new ArrayList<>();
				for (Usuario aluno : time.getAlunos()) {
					IdNomeUsuarioDTO dto = new IdNomeUsuarioDTO();
					dto.setId(aluno.getIdUsuario());
					dto.setMatricula(aluno.getMatricula());
					dto.setNome(aluno.getNome());
					alunosProjeto.add(dto);
				}
				List<IdNomeUsuarioDTO> alunosDisponiveis = usuarioBO.alunosElegiveis();
				request.setAttribute("time", time);
				request.setAttribute("alunosProjeto", alunosProjeto);
				request.setAttribute("alunos", alunosDisponiveis);
				//	request.setAttribute("listaStakeholders", stakeholders);


			} else {
				
				proxima = "time/addTime.jsp";

				usuarioBO = new UsuarioBO();
				List<IdNomeUsuarioDTO> alunos = usuarioBO.alunosElegiveis();				

				Calendar hoje = Calendar.getInstance();
				String ano = Integer.toString(hoje.get(hoje.YEAR));
				System.out.println(ano);
				request.setAttribute("ano", ano);

				request.setAttribute("alunos", alunos);
			}
		} catch(Exception e){
			request.setAttribute("msgErro", e.getMessage());
		}

		return proxima;
	}
}
