package br.ages.crud.bo;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import br.ages.crud.dao.AulaDAO;
import br.ages.crud.dao.ProjetoDAO;
import br.ages.crud.dao.TurmaDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Aula;
import br.ages.crud.model.Presenca;
import br.ages.crud.model.Projeto;
import br.ages.crud.model.Turma;
//import br.ages.crud.model.Status;
import br.ages.crud.util.MensagemContantes;
import br.ages.crud.validator.DataValidator;

public class AulaBO {

	private AulaDAO aulaDAO;

	public AulaBO() {
		aulaDAO = new AulaDAO();
	}
	
	/*public boolean validarTurma(Turma turma) throws NegocioException {
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
	}*/

	public boolean cadastrarDiasAulas(List<Aula> aulas) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = aulaDAO.cadastrarDiasAulas(aulas);
		if (ok == false)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_DIAS_AULAS_TURMA);
		return ok;
	}
	
	public boolean removerDiasAulas(List<Aula> aulas) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = aulaDAO.removerDiasAulas(aulas);
		if (ok == false)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_DIAS_AULAS_TURMA);
		return ok;
	}
	
	public LocalDate primeiroDia(int semestre, int ano) throws SQLException, ParseException, NegocioException, PersistenciaException {
		LocalDate ok = null;
		ok = aulaDAO.primeiroDia(semestre, ano);
		if (ok == null)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_DIAS_AULAS_TURMA);
		return ok;
	}
	
	public boolean cadastrarPresencasAula(Aula aula) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = aulaDAO.cadastrarPresencasAula(aula);
		if (ok == false)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_PRESENCAS_AULA);
		return ok;
	}
	
	public boolean cadastrarPresencas(List<Presenca> presencas) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = aulaDAO.cadastrarPresencas(presencas);
		if (ok == false)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_PRESENCAS_AULA);
		return ok;
	}
	
	public boolean excluirPresencas(List<Presenca> presencas) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = aulaDAO.excluirPresencas(presencas);
		if (ok == false)
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_PRESENCAS_AULA);
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
	/*public void editaTurma(Turma turma) throws NegocioException {

		try {
			turmaDAO.editaTurma(turma);
		} catch (PersistenciaException | SQLException | ParseException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

	}*/

	public List<Aula> listarDiasAulasTurma(int idTurma) throws NegocioException {
		List<Aula> listAula = null;

		try {
			listAula = aulaDAO.listarDiasAulasTurma(idTurma);
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return listAula;
	}
	
	public List<Presenca> listarPresencasAula(int idAula) throws NegocioException {
		List<Presenca> listPresencas = null;

		try {
			listPresencas = aulaDAO.listarPresencasAula(idAula);
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return listPresencas;
	}
	
	public List<Presenca> listarPresencasTurma(int idTurma) throws NegocioException {
		List<Presenca> listPresencas = null;

		try {
			listPresencas = aulaDAO.listarPresencasTurma(idTurma);
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return listPresencas;
	}
	
	public List<Presenca> listarPresencasTurmaMes(int idTurma, int mes) throws NegocioException {
		List<Presenca> listPresencas = null;

		try {
			listPresencas = aulaDAO.listarPresencasTurmaMes(idTurma, mes);
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return listPresencas;
	}
	
	public List<Presenca> listarPresencasAlunoTurma(int idAluno, int idTurma) throws NegocioException {
		List<Presenca> listPresencas = null;

		try {
			listPresencas = aulaDAO.listarPresencasAlunoTurma(idAluno, idTurma);
		} catch (PersistenciaException | SQLException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}

		return listPresencas;
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
	/*public Turma buscarTurma(int idTurma) throws NegocioException, PersistenciaException, SQLException {
		Turma turma= turmaDAO.buscaTurma(idTurma);
		return turma;
	}
 */
	public void setAulaDAO(AulaDAO aulaDAO) {
		this.aulaDAO = aulaDAO;
	}

}