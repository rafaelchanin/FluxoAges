package br.ages.crud.command;

import br.ages.crud.bo.TipoEquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.TipoEquipamento;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.ArrayList;

public class ListarTipoEquipamentoCommand implements Command {
    private String proxima;
    private TipoEquipamentoBO tipoequipamentoBO;

    public String execute(HttpServletRequest request) throws SQLException {
        this.tipoequipamentoBO = new TipoEquipamentoBO();
        proxima = "listaequipamento/listaTiposEquipamentos.jsp";

        try {
            ArrayList<TipoEquipamento> listaTipoEquipamentos = tipoequipamentoBO.listarEquipamentos();
            request.setAttribute("listaTiposEquipamentos", listaTipoEquipamentos);
        } catch (NegocioException e) {
            e.printStackTrace();
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
