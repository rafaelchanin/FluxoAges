package br.ages.crud.command;

import br.ages.crud.bo.EquipamentoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;

public class CreateScreenEquipamentoCommand implements Command {

    private String proxima;
    private EquipamentoBO equipamentoBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
        proxima = "main?acao=list"
    }
}
