package br.ages.crud.bo;

import br.ages.crud.dao.TipoEquipamentoDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.TipoEquipamento;

import java.sql.SQLException;
import java.util.ArrayList;

public class TipoEquipamentoBO {
    private TipoEquipamentoDAO tipoequipamentosDAO;

    public ArrayList<TipoEquipamento> listarEquipamentos() throws NegocioException {

        ArrayList<TipoEquipamento> equipamentos = new ArrayList<>();
        tipoequipamentosDAO = new TipoEquipamentoDAO();
        try {
            equipamentos = tipoequipamentosDAO.listarTipoEquipamentos();
        } catch (PersistenciaException | SQLException e) {
            e.printStackTrace();
            throw new NegocioException(e);
        }

        return equipamentos;
    }
}
