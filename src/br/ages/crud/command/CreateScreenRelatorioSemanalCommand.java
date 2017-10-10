package br.ages.crud.command;

import br.ages.crud.bo.TimePontoDTOBO;
import br.ages.crud.exception.NegocioException;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.model.TimePontoDTO;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class CreateScreenRelatorioSemanalCommand implements Command {
    private String proxima;
    private TimePontoDTOBO timePontoDTOBO;
    private List<TimePontoDTO> listaTimes;
    private LocalDate hoje;


    @Override
    public String execute(HttpServletRequest request) throws SQLException {
        timePontoDTOBO = new TimePontoDTOBO();

        proxima = "aluno/relatorio.jsp";

        try {
            listaTimes = timePontoDTOBO.listarTimes();

            hoje = LocalDate.now();




            //Sets
            request.setAttribute("listaTimes", listaTimes);
            request.setAttribute("dia",hoje);
        } catch (NegocioException e) {
            e.printStackTrace();
            request.setAttribute("msgErro",e.getMessage());
        }


        return proxima;
    }
}
