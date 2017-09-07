package br.ages.crud.command;

import br.ages.crud.bo.EquipamentoAlunoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.EquipamentoAluno;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class EntregaEquipamentoCommand implements Command {

    private String proxima;
    private EquipamentoAlunoBO equipamentoAlunoBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
        proxima = "main?acao=listAlunosEquipamentos";
        equipamentoAlunoBO = new EquipamentoAlunoBO();

        int id = Integer.parseInt(request.getParameter("id"));

        try{
            equipamentoAlunoBO.entregarEquipamento(id);

        } catch (NegocioException e) {
            e.printStackTrace();
            request.setAttribute("msgErro", e.getMessage());
        }


        return proxima;
    }
}
