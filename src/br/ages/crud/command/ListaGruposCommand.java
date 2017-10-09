package br.ages.crud.command;

import br.ages.crud.bo.GrupoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.model.Grupo;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class ListaGruposCommand implements Command {

    private String proxima;
    private GrupoBO grupoBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        this.grupoBO = new GrupoBO();
        proxima = "grupo/listGrupo.jsp";

        try {
            List<Grupo> listaGrupos = grupoBO.listarGrupos();
            request.setAttribute("listaGrupos", listaGrupos);
        } catch (NegocioException e) {
            e.printStackTrace();
            request.setAttribute("msgErro", e.getMessage());
        }
        return proxima;
    }
}
