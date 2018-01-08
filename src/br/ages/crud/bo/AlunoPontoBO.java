package br.ages.crud.bo;

import br.ages.crud.dao.AlunoPontoDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.AlunoPonto;

import java.sql.SQLException;
import java.util.List;

public class AlunoPontoBO {

    private AlunoPontoDAO alunoPontoDAO;

    public AlunoPontoBO() {
        alunoPontoDAO = new AlunoPontoDAO();
    }

    public List<AlunoPonto> listarPonto(int idAluno) throws NegocioException {
        List<AlunoPonto> listaPonto = null;
        try{
            listaPonto = alunoPontoDAO.listarPonto(idAluno);
        } catch (SQLException e){
            throw new NegocioException(e);
        }
        return listaPonto;
    }
}
