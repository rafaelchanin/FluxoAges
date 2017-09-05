package br.ages.crud.command;

import br.ages.crud.bo.EquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Equipamento;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListarEquipamentosCommand implements Command {
    private String proxima;
    private EquipamentoBO equipamentoBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        this.equipamentoBO = new EquipamentoBO();
        proxima = "equipamento/listEquipamento.jsp";

        try {
            ArrayList<Equipamento> listaEquipamentos = equipamentoBO.listarEquipamentos();
            request.setAttribute("listaEquipamentos", listaEquipamentos);
        } catch (NegocioException e) {
            e.printStackTrace();
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
