package br.ages.crud.command;

import br.ages.crud.bo.RelatorioBO;
import br.ages.crud.bo.TimeRelatorioBO;
import br.ages.crud.model.Relatorio;
import br.ages.crud.model.TimeRelatorio;
import br.ages.crud.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.List;

public class ListarRelatorioSemanalProfessor implements Command {
    private String proxima;
    private TimeRelatorioBO timeRelatorioBO;
    private List<TimeRelatorio> listaRelatorio;

    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        timeRelatorioBO = new TimeRelatorioBO();
        proxima = "professor/listRelatorioSemanalProfessor.jsp";

        try{
            listaRelatorio = timeRelatorioBO.listarTimes();
        }catch (Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }

        request.setAttribute("listaRelatorio", listaRelatorio);
        return proxima;
    }
}
