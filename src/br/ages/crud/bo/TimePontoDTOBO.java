package br.ages.crud.bo;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import br.ages.crud.dao.TimeDAO;
import br.ages.crud.dao.TimePontoDTODAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Time;
import br.ages.crud.model.TimePontoDTO;

public class TimePontoDTOBO {

	
	
	private TimePontoDTODAO timePontoDTODAO;
	
	public TimePontoDTOBO(){
		timePontoDTODAO = new TimePontoDTODAO();
	}
	
	public List<TimePontoDTO> listarTimes() throws NegocioException{
		List<TimePontoDTO> listTime = null;
		try {
			listTime = timePontoDTODAO.listarTimes();
		} catch (PersistenciaException | SQLException e){
			throw new NegocioException(e);
		}
		return listTime;
	}

	public List<TimePontoDTO> listarTimerProfessor(int idProfessor) throws NegocioException {
		List<TimePontoDTO> timePontoDTOList = null;
		try{
			timePontoDTOList = timePontoDTODAO.listarTimesProfessor(idProfessor);
		} catch (PersistenciaException e){

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  timePontoDTOList;
	}
}
