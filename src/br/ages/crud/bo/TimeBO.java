package br.ages.crud.bo;

import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.util.MensagemContantes;

import java.util.List;
import java.sql.SQLException;
import java.text.ParseException;

import br.ages.crud.dao.TimeDAO;
import br.ages.crud.model.Time;


public class TimeBO {
	private TimeDAO timeDAO;
	
	public TimeBO(){
		timeDAO = new TimeDAO();
	}
	
	public boolean validarTime(Time time) throws NegocioException{
		boolean valido = true;
		
		StringBuilder msg = new StringBuilder();
		msg.append(MensagemContantes.MSG_ERR_TIME_DADOS_INVALIDOS.concat("<br/>"));

			//DataValidator validator = new DataValidator();

			if (time.getStatus() == null || time.getStatus().equals("")) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_INVALIDO.replace("?", "Status ").concat("<br/>"));
			}
			if (time.getAno() == 0) {
				valido = false;
				msg.append(MensagemContantes.MSG_ERR_CAMPO_OBRIGATORIO.replace("?", "Ano ").concat("<br/>"));
			}
			
			///TODO
			
			if (!valido) {
				throw new NegocioException(msg.toString());
			}
		return valido;
	}
	
	public boolean cadastrarTime(Time time) throws SQLException, ParseException, NegocioException, PersistenciaException {
		boolean ok = false;
		ok = timeDAO.cadastrarTime(time);
		if (ok == false)
			//TODO fazer a mensagem "CONTANTE" apropriada
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_TIME);
		return ok;
	}
	
	public void editarTime(Time time) throws NegocioException {
		try {
			timeDAO.editarTime(time);
		} catch (PersistenciaException | SQLException | ParseException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
	}
	
	public Time buscarTime(int idTime) throws NegocioException, PersistenciaException, SQLException {
		return timeDAO.buscaTime(idTime);
	}
	public List<Time> listarTimes() throws NegocioException{
		List<Time> listTime = null;
		try {
			listTime = timeDAO.listarTimes();
		} catch (PersistenciaException | SQLException e){
			throw new NegocioException(e);
		}
		return listTime;
	}

	public void removerTime(Time time) throws NegocioException {
		try {
			timeDAO.removerTime(time);
		} catch (PersistenciaException e) {
			e.printStackTrace();
			throw new NegocioException(e);
		}
		
	}


	public Time buscarTimeIdTimeAluno(int idTimeAluno) {
		Time time;

		int idTime = timeDAO.buscaTimeIdTimeAluno(idTimeAluno);

		time = timeDAO.buscaTime(idTime);


		return time;
	}
}
