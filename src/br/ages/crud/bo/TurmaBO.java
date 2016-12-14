package br.ages.crud.bo;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import br.ages.crud.dao.ProjetoDAO;
import br.ages.crud.dao.TurmaDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Projeto;
import br.ages.crud.model.Turma;
//import br.ages.crud.model.Status;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.validator.DataValidator;

public class TurmaBO {

	private TurmaDAO turmaDAO;

	public TurmaBO() {
		turmaDAO = new TurmaDAO();
	}

	public boolean cadastrarTurma(Turma turma) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = turmaDAO.cadastrarTurma(turma);
		if (ok == false)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_PROJETO);
		return ok;
	}
/*
	public boolean validarProjeto(Projeto project) throws NegocioException {
		boolean valido = true;
		StringBuilder msg = new StringBuilder();
		msg.append(MensagemContantes.MSG_ERR_PROJETO_DADOS_INVALIDOS.concat("<br/>"));

			DataValidator validator = new DataValidator();

			if (project.getStatusProjeto() == null) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_INVALIDO.replace("?", "Status ").concat("<br/>"));
			}
			if (project.getNomeProjeto() == null || project.getNomeProjeto().equals("")) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Nome do projeto ").concat("<br/>"));
			}
			if (project.getDataInicio() == null) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Data de início ").concat("<br/>"));
			}
			if (project.getDataFimPrevisto() == null) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Data de fim previsto ").concat("<br/>"));
			}
			if (!validator.maisCedoQue(project.getDataInicio(), project.getDataFimPrevisto())) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_PROJETO_DATA_INCONSISTENTE.replace("?", " previsto").concat("<br/>"));
			}

			if (!valido) {
				throw new NegocioException(msg.toString());
			}
		return valido;
	}
*/
	public void editaTurma(Turma turma) throws NegocioException {

		try {
			turmaDAO.editaTurma(turma);
		} catch (PersistenciaException | SQLException | ParseException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

	}

	public List<Turma> listarTurmas() throws NegocioException {
		List<Turma> listTurma = null;

		try {
			listTurma = turmaDAO.listarTurmas();
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return listTurma;
	}
/*
	public void removerProjeto(Projeto project) throws NegocioException {
		try {
			projetoDAO.removerProjeto(project);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}
*/
/*	public Projeto buscarProjeto(int idProjeto) throws NegocioException {
		try {
			Projeto projeto = projetoDAO.consultarProjeto(idProjeto);
			return projeto;
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}
*/
	public void setTurmaDAO(TurmaDAO turmaDAO) {
		this.turmaDAO = turmaDAO;
	}

}