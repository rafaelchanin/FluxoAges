package br.ages.crud.command;

import br.ages.crud.bo.ProjetoBO;
import br.ages.crud.bo.RelatorioBO;
import br.ages.crud.bo.TimeBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Relatorio;
import br.ages.crud.model.Time;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class VisualizarRelatorioCommand implements Command {
    private String proxima;
    private RelatorioBO relatorioBO;
    private TimeBO timeBO;
    private ProjetoBO projetoBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {

        proxima = "professor/relatorioVisualizacao.jsp";

        relatorioBO = new RelatorioBO();

        int idRelatorio = Integer.parseInt(request.getParameter("id_relatorio"));

        timeBO = new TimeBO();
        projetoBO = new ProjetoBO();

        Relatorio relatorio = relatorioBO.buscaRelatorioId(idRelatorio);

        Time time = timeBO.buscarTimeIdTimeAluno(relatorio.getIdTimeAluno());
        try {
            time.setProjetoNome(projetoBO.buscarProjeto(time.getProjeto()));
        } catch (NegocioException e) {
            e.printStackTrace();
        }

        request.setAttribute("relatorio", relatorio);
        request.setAttribute("time", time.toString());



        return proxima;
    }
}
