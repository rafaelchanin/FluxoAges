package br.ages.crud.bo;

import br.ages.crud.dao.EquipamentoDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;

import java.sql.SQLException;
import java.util.ArrayList;

public class EquipamentoBO {

    private EquipamentoDAO equipamentoDAO;

    public ArrayList<Equipamento> listarEquipamentos() throws NegocioException {

        ArrayList<Equipamento> equipamentos = new ArrayList<>();
        equipamentoDAO = new EquipamentoDAO();
        try {
            equipamentos = equipamentoDAO.listarEquipamentos();
        } catch (PersistenciaException | SQLException e) {
            e.printStackTrace();
            throw new NegocioException(e);
        }

        return equipamentos;
    }
}
