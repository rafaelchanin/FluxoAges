package br.ages.crud.command;

import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.ages.crud.bo.ProjetoBO;
import br.ages.crud.bo.TurmaBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Projeto;
import br.ages.crud.model.Turma;

public class ListaTurmasCommand implements Command{
	private String proxima;
	private TurmaBO turmaBO;
	
	@Override
	public String execute(HttpServletRequest request) throws SQLException {
		this.turmaBO = new TurmaBO();
		proxima = "turma/listTurma.jsp";

		try {
			List<Turma> listaTurmas = turmaBO.listarTurmas();
			request.setAttribute("listaTurmas", listaTurmas);
		} catch (NegocioException e) {
			e.printStackTrace();
			request.setAttribute("msgErro", e.getMessage());
		}
		return proxima;
	}
}

