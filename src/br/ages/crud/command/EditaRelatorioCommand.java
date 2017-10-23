package br.ages.crud.command;

import br.ages.crud.bo.RelatorioBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Relatorio;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class EditaRelatorioCommand implements Command {

    private String proxima;

    private RelatorioBO relatorioBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException {

        relatorioBO = new RelatorioBO();
        Relatorio relatorio = new Relatorio();

        proxima = "aluno/editRelatorio.jsp";

        String previstas = request.getParameter("previstas");
        String concluidas = request.getParameter("concluidas");
        String problemas = request.getParameter("problemas");
        String proximo = request.getParameter("proximos");
        String idRelatorio = request.getParameter("idRelatorio");


        try{
            relatorio.setAtividadesPrevistas(previstas);
            relatorio.setAtividadesConcluidas(concluidas);
            relatorio.setLicoesProblemas(problemas);
            relatorio.setProximo(proximo);
            relatorio.setIdRelatorio(Integer.parseInt(idRelatorio));

            request.setAttribute("relatorio",relatorio);

            boolean valido = relatorioBO.validarRelatorio(relatorio);

            if(valido){
                relatorioBO.editarRelatorio(relatorio);
                proxima = "main?acao=listaRelatorios";
                request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_EDICAO_RELATORIO);
            }
        }catch (Exception e){
            request.setAttribute("msgErro",e.getMessage());
        }








        return proxima;
    }
}
