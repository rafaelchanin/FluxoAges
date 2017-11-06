package br.ages.crud.command;

import br.ages.crud.bo.RelatorioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class RecusarRelatorioCommand implements Command {
    private String proxima;
    private RelatorioBO relatorioBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
        proxima = "main?acao=listaRelatoriosSemanaisProfessor";

        relatorioBO = new RelatorioBO();

        int idRelatorio = Integer.parseInt(request.getParameter("id_relatorio"));

        relatorioBO.recusar(idRelatorio);


        return proxima;
    }
}
