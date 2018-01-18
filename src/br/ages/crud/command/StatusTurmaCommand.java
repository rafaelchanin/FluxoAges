package br.ages.crud.command;

import br.ages.crud.bo.TurmaBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class StatusTurmaCommand implements Command {
    String proxima;
    TurmaBO turmaBO;

    @Override
    public String execute(HttpServletRequest request) {
        turmaBO = new TurmaBO();
        proxima = "main?acao=listaTurmas";
        String idTurma = request.getParameter("idTurma");
        String statusTurma = request.getParameter("status");

        int id = Integer.parseInt(idTurma);

        try{
            turmaBO.editarStatus(id,statusTurma);
        }catch (Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }finally {
            request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_STATUS_TURMA);
        }

        return proxima;
    }
}
