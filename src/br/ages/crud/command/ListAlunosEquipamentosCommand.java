package br.ages.crud.command;

import br.ages.crud.bo.EquipamentoAlunoBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.EquipamentoAluno;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

public class ListAlunosEquipamentosCommand implements Command {

    private String proxima;
    private EquipamentoAlunoBO equipamentoAlunoBO;

    @Override
    public String execute(HttpServletRequest request) throws SQLException, NegocioException, ParseException, PersistenciaException {
        equipamentoAlunoBO = new EquipamentoAlunoBO();
        proxima = "equipamento/listaAlunosEquipamentos.jsp";


        try {
            ArrayList<EquipamentoAluno> equipamentoAlunos = equipamentoAlunoBO.listarEquipamentosAlunos();
            request.setAttribute("lista", equipamentoAlunos);


        } catch (Exception e) {
            request.setAttribute("msgErro", e.getMessage());
        }

        return proxima;
    }
}
