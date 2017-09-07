package br.ages.crud.bo;

import br.ages.crud.dao.EquipamentoAlunoDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.EquipamentoAluno;

import java.sql.SQLException;
import java.util.ArrayList;

public class EquipamentoAlunoBO {

    private EquipamentoAlunoDAO equipamentoAlunoDAO;

    public ArrayList<EquipamentoAluno> listarEquipamentosAlunos() throws NegocioException {

        ArrayList<EquipamentoAluno> equipamentoAlunos = new ArrayList<>();
        equipamentoAlunoDAO = new EquipamentoAlunoDAO();
        try {
            equipamentoAlunos = equipamentoAlunoDAO.listarEquipamentosAlunos();
        } catch (PersistenciaException | SQLException e) {
            e.printStackTrace();
            throw new NegocioException(e);
        }

        return equipamentoAlunos;
    }

    public boolean entregarEquipamento(int id) throws NegocioException {
        boolean ok = false;
        equipamentoAlunoDAO = new EquipamentoAlunoDAO();
        try {
            ok = equipamentoAlunoDAO.entregarEquipamento(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NegocioException(e);
        }
        return ok;
    }
}
