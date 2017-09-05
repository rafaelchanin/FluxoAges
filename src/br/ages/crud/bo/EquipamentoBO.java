package br.ages.crud.bo;

import br.ages.crud.dao.EquipamentoDAO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Equipamento;
import br.ages.crud.util.MensagemContantes;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class EquipamentoBO {

    private EquipamentoDAO equipamentoDAO;

    public boolean cadastrarEquipamento(Equipamento equipamento) throws SQLException, ParseException, NegocioException, PersistenciaException {
        boolean ok = false;
        ok = equipamentoDAO.cadastrarEquipamento(equipamento);
        if (ok == false)
            throw new NegocioException(MensagemContantes.MSG_ERR_CADASTRO_EQUIPAMENTO);
        return ok;
    }

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
