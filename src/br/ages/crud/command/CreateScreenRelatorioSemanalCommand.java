package br.ages.crud.command;

import br.ages.crud.bo.*;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateScreenRelatorioSemanalCommand implements Command {
    private String proxima;
    private AlunoPontoBO alunoPontoBO;
    private List<AlunoPonto> listaTimes;
    private LocalDate dia;
    private RelatorioBO relatorioBO;
    private TimeBO timeBO;
    private ProjetoBO projetoBO;


    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        alunoPontoBO = new AlunoPontoBO();

        proxima = "aluno/relatorio.jsp";


        String isEdit = request.getParameter("isEdit");
        if(isEdit != null && !"".equals(isEdit)){
            relatorioBO = new RelatorioBO();
            timeBO = new TimeBO();
            projetoBO = new ProjetoBO();

            int id = Integer.parseInt(request.getParameter("id_relatorio"));

            Relatorio relatorio = relatorioBO.buscaRelatorioId(id);

            Time time = timeBO.buscarTimeIdTimeAluno(relatorio.getIdTimeAluno());
            try {
                time.setProjetoNome(projetoBO.buscarProjeto(time.getProjeto()));
            } catch (NegocioException e) {
                e.printStackTrace();
            }

            request.setAttribute("relatorio", relatorio);
            request.setAttribute("time", time.toString());

            proxima = "aluno/editRelatorio.jsp";
        }else {
            try {
                Usuario aluno = (Usuario) request.getSession().getAttribute("usuarioSessao");
                listaTimes = alunoPontoBO.listarPonto(aluno.getIdUsuario());

                dia = LocalDate.now();

                Calendar hoje = Calendar.getInstance();
                String data = Integer.toString(hoje.get(hoje.DAY_OF_MONTH)) + "/" + Integer.toString(hoje.get(hoje.MONTH) + 1) + "/" + Integer.toString(hoje.get(hoje.YEAR));

                //Sets
                request.setAttribute("listaTimes", listaTimes);
                request.setAttribute("dia", dia);
                request.setAttribute("data", data);
            } catch (NegocioException e) {
                e.printStackTrace();
                request.setAttribute("msgErro", e.getMessage());
            }
        }


        return proxima;
    }
}
