package br.ages.crud.bo;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import br.ages.crud.dao.ProjetoDAO;
import br.ages.crud.dao.TurmaDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Presenca;
import br.ages.crud.model.Projeto;
import br.ages.crud.model.Turma;
import br.ages.crud.model.Usuario;
//import br.ages.crud.model.Status;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.validator.DataValidator;

public class TurmaBO {

	private TurmaDAO turmaDAO;

	public TurmaBO() {
		turmaDAO = new TurmaDAO();
	}
	
	public boolean validarTurma(Turma turma) throws NegocioException {
		boolean valido = true;
		StringBuilder msg = new StringBuilder();
		msg.append(MensagemContantes.MSG_ERR_TURMA_DADOS_INVALIDOS.concat("<br/>"));

			DataValidator validator = new DataValidator();

			if (turma.getStatus() == null || turma.getStatus().equals("")) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_INVALIDO.replace("?", "Status ").concat("<br/>"));
			}
			if (turma.getNumero() == 0) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Número da Turma ").concat("<br/>"));
			}
			if (turma.getAges() == 0) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Ages ").concat("<br/>"));
			}
			if (turma.getAno() == 0) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Ano ").concat("<br/>"));
			}
			
			if (!valido) {
				throw new NegocioException(msg.toString());
			}
		return valido;
	}
	
	
	public boolean validarAlunos(int idTurma, List<Usuario> adicionar, List<Usuario> remover) throws NegocioException {
		boolean valido = true;
		AulaBO aulaBO = new AulaBO();
		StringBuilder msg = new StringBuilder();
		msg.append(MensagemContantes.MSG_ERR_TURMA_DADOS_INVALIDOS.concat("<br/>"));
		DataValidator validator = new DataValidator();
		List<Presenca> presencas = aulaBO.listarPresencasTurma(idTurma);
		
		for (Usuario alunoRemover : remover) {
			if (valido==false)
				break;
			for (Presenca presenca : presencas) {
				if (presenca.getAluno().getIdUsuario() == alunoRemover.getIdUsuario()) {
					valido=false;
					msg.append(MensagemContantes.MSG_ERR_TURMA_ALUNOS_INVALIDOS.concat("<br/>"));
					break;
				}
			}
		}	
			if (!valido) {
				throw new NegocioException(msg.toString());
			}
		return valido;
	}

	public boolean cadastrarTurma(Turma turma) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = turmaDAO.cadastrarTurma(turma);
		if (ok == false)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_TURMA);
		return ok;
	}
	
	public boolean inserirAlunosTurma(int idTurma, List<Usuario> alunos) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = turmaDAO.inserirAlunosTurma(idTurma, alunos);
		if (ok == false)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_TURMA);
		return ok;
	}
	
	public boolean removerAlunosTurma(int idTurma, List<Usuario> alunos) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = turmaDAO.removerAlunosTurma(idTurma, alunos);
		if (ok == false)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_TURMA);
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
	
	public List<Turma> listarTurmasAtivas() throws NegocioException {
		List<Turma> listTurma = null;

		try {
			listTurma = turmaDAO.listarTurmasAtivas();
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
	public Turma buscarTurma(int idTurma) throws NegocioException, PersistenciaException, SQLException {
		Turma turma= turmaDAO.buscaTurma(idTurma);
		return turma;
	}

	public void setTurmaDAO(TurmaDAO turmaDAO) {
		this.turmaDAO = turmaDAO;
	}

}