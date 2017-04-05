package br.ages.crud.bo;

import java.sql.SQLException;
import java.util.ArrayList;

import br.ages.crud.dao.PeriodoDAO;
import br.ages.crud.dao.PontoDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Periodo;
import br.ages.crud.model.Ponto;
import br.ages.crud.util.MensagemContantes;

public class PeriodoBO {
	private PeriodoDAO periodoDAO;
	
	public ArrayList<Periodo> listaPeriodo() throws NegocioException, SQLException, PersistenciaException {
		periodoDAO = new PeriodoDAO();
		ArrayList<Periodo> listaPeriodo = new ArrayList<Periodo>();
		try {
			listaPeriodo = periodoDAO.listaPeriodo();
		} catch (NegocioException e) {
			e.printStackTrace();
			throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_PONTO);
		}
			return listaPeriodo;
		
		
	}
}
