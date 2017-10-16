package br.ages.crud.command;

import br.ages.crud.bo.RelatorioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Relatorio;
import br.ages.crud.model.StatusRelatorio;
import br.ages.crud.model.TipoRelatorio;
import br.ages.crud.model.Usuario;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;

public class EnviarRelatorioCommand implements Command {

    private String proxima;
    private Relatorio relatorio;
    private RelatorioBO relatorioBO;
    private LocalDate hoje;

    @Override
    public String execute(HttpServletRequest request){
        relatorio = new Relatorio();
        relatorioBO = new RelatorioBO();

        proxima = "main?acao=relatorioSemanal";

        hoje = LocalDate.now();


        String atividadesPrevistas = request.getParameter("previstas");
        String atividadesConcluidas = request.getParameter("concluidas");
        String licoesProblemas = request.getParameter("problemas");
        String proximo = request.getParameter("proximos");
        SimpleDateFormat textFormat = new SimpleDateFormat("dd/MM/yyyy");
        String semana = request.getParameter("dia");
        Usuario aluno = (Usuario) request.getSession().getAttribute("usuarioSessao");
        String time = request.getParameter("time");


        try {
            relatorio.setDtInclusao(Date.from(hoje.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            relatorio.setStatus(StatusRelatorio.REVISAO);
            relatorio.setTipo(TipoRelatorio.SEMANAL);

            if(!semana.equals("")) {
                relatorio.setInicioSemana(textFormat.parse(semana));
            }
            if(!atividadesPrevistas.equals("")){
                relatorio.setAtividadesPrevistas(atividadesPrevistas);
            }
            if(!atividadesConcluidas.equals("")){
                relatorio.setAtividadesConcluidas(atividadesConcluidas);
            }
            if(!licoesProblemas.equals("")){
                relatorio.setLicoesProblemas(licoesProblemas);
            }
            if(!proximo.equals("")){
                relatorio.setProximo(proximo);
            }
            if(aluno != null && !time.equals("")){
                int idTimeAluno = relatorioBO.validaAluno(aluno,Integer.parseInt(time));
                if(idTimeAluno > 0){
                    relatorio.setIdTimeAluno(idTimeAluno);
                }
            }

            if(relatorioBO.validarRelatorio(relatorio)){
                relatorioBO.cadastrarRelatorio(relatorio);
            }

        }catch (Exception e) {
            request.setAttribute("msgErro", e.getMessage());
        }
        return proxima;
    }
}
