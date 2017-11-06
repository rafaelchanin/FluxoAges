package br.ages.crud.bo;


import br.ages.crud.dao.TimeRelatorioDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.TimeRelatorio;

import java.sql.SQLException;
import java.util.List;

public class TimeRelatorioBO {
    private TimeRelatorioDAO timeRelatorioDAO;

    public TimeRelatorioBO() {
        this.timeRelatorioDAO = new TimeRelatorioDAO();
    }

    public List<TimeRelatorio> listarTimes() throws NegocioException{
        List<TimeRelatorio> listTime = null;
        try {
            listTime = timeRelatorioDAO.listarTimes();
        } catch (PersistenciaException | SQLException e){
            throw new NegocioException(e);
        }
        return listTime;
    }
}
