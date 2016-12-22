package br.ages.crud.command;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.ProjetoBO;
import br.ages.crud.bo.TimeBO;
import br.ages.crud.bo.UsuarioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Time;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

public class RemoveTimeCommand implements Command{

	private String proximo;
	private TimeBO timeBO;

	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		proximo = "main?acao=listaTimes";
		timeBO = new TimeBO();

		Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioSessao");		

		try {
			if( !usuario.getPerfilAcesso().equals(PerfilAcesso.ADMINISTRADOR) ) throw new NegocioException(MensagemContantes.MSG_INF_DENY);

			Integer idTime = Integer.parseInt(request.getParameter("id_time"));

			Time time = new Time();
			time.setId(idTime);
			//TODO
			timeBO.removerTime(time);
			request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_REMOVE_PROJETO.replace("?", idTime.toString()).concat("<br/>")); 

		} catch (Exception e) {
			request.setAttribute("msgErro", e.getMessage());
		}

		return proximo;
	}

}
