package br.ages.crud.command;

import br.ages.crud.bo.TimeBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.Status;
import br.ages.crud.util.MensagemContantes;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class StatusTimeCommand implements Command {
    TimeBO timeBO;
    String proxima;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
        timeBO = new TimeBO();
        proxima = "main?acao=listaTimes";
        String idTime = request.getParameter("idTime");
        String statusTime = request.getParameter("status");

        int id = Integer.parseInt(idTime);

        try {
            timeBO.editarStatus(id, statusTime);

        }catch(Exception e){
            request.setAttribute("msgErro", e.getMessage());
        }finally {

            request.setAttribute("msgSucesso", MensagemContantes.MSG_SUC_STATUS_TIME);
        }
        return proxima;
    }
}
