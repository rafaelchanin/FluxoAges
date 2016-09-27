package br.ages.crud.command;

import java.sql.SQLException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.PontoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.PerfilAcesso;
import br.ages.crud.model.Usuario;
import br.ages.crud.util.MensagemContantes;

public class RemoverPontoAlunoCommand implements Command {

	private String proximo;
	private PontoBO pontoBO;
	
	@Override
	public String execute(HttpServletRequest request) {
	proximo = "main?acao=listUser";
	this.pontoBO = new PontoBO();
	
	//Usuario usuario = (Usuario)request.getSession().getAttribute("usuarioSessao");		

	try {
		Integer idPonto = Integer.parseInt(request.getParameter("id_ponto"));
		pontoBO.removePonto(idPonto);
		
		request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_REM_PONTO);
		
	} catch (Exception e) {
		request.setAttribute("msgErro", e.getMessage());
	}
	
	return proximo;
}

}
