package br.ages.crud.bo;

import br.ages.crud.dao.TipoEquipamentoDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.model.TipoEquipamento;
import br.ages.crud.util.MensagemContantes;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class TipoEquipamentoBO {
    private TipoEquipamentoDAO tipoequipamentosDAO;

    public ArrayList<TipoEquipamento> listarTipoEquipamentos() throws NegocioException {

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

    public TipoEquipamento buscarEquipamentoPorId(int id) throws NegocioException {
        tipoequipamentosDAO = new TipoEquipamentoDAO();
        try{
            return tipoequipamentosDAO.buscarEquipamentoPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new NegocioException(e);
        }
    }

    public boolean cadastrarTipoEquipamento(TipoEquipamento tipoEquipamento) throws PersistenciaException, SQLException {
        boolean ok = false;
        tipoequipamentosDAO = new TipoEquipamentoDAO();
        return tipoequipamentosDAO.cadastrarTipoEquipamento(tipoEquipamento);
    }
}
